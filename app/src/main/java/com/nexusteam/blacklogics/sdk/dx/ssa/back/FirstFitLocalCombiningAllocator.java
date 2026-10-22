
package mod.agus.jcoderz.dx.ssa.back;

import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.ssa.InterferenceRegisterMapper;
import mod.agus.jcoderz.dx.ssa.NormalSsaInsn;
import mod.agus.jcoderz.dx.ssa.Optimizer;
import mod.agus.jcoderz.dx.ssa.PhiInsn;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.ssa.SsaBasicBlock;
import mod.agus.jcoderz.dx.ssa.SsaInsn;
import mod.agus.jcoderz.dx.ssa.SsaMethod;
import mod.agus.jcoderz.dx.util.IntIterator;
import mod.agus.jcoderz.dx.util.IntSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.code.CstInsn;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;

public class FirstFitLocalCombiningAllocator extends RegisterAllocator {

    private enum Alignment {
      EVEN {
        @Override
        int nextClearBit(BitSet bitSet, int startIdx) {
          int bitNumber = bitSet.nextClearBit(startIdx);
          while (!isEven(bitNumber)) {
            bitNumber = bitSet.nextClearBit(bitNumber + 1);
          }
          return bitNumber;
        }
      },
      ODD {
        @Override
        int nextClearBit(BitSet bitSet, int startIdx) {
          int bitNumber = bitSet.nextClearBit(startIdx);
          while (isEven(bitNumber)) {
            bitNumber = bitSet.nextClearBit(bitNumber + 1);
          }
          return bitNumber;
        }
      },
      UNSPECIFIED {
        @Override
        int nextClearBit(BitSet bitSet, int startIdx) {
          return bitSet.nextClearBit(startIdx);
        }
      };

      abstract int nextClearBit(BitSet bitSet, int startIdx);
    }

    private static final boolean DEBUG = false;

    private final Map<mod.agus.jcoderz.dx.rop.code.LocalItem, ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec>> localVariables;

    private final ArrayList<NormalSsaInsn> moveResultPseudoInsns;

    private final ArrayList<NormalSsaInsn> invokeRangeInsns;

    private final ArrayList<PhiInsn> phiInsns;

    private final BitSet ssaRegsMapped;

    private final InterferenceRegisterMapper mapper;

    private final int paramRangeEnd;

    private final BitSet reservedRopRegs;

    private final BitSet usedRopRegs;

    private final boolean minimizeRegisters;

    public FirstFitLocalCombiningAllocator(
            SsaMethod ssaMeth, InterferenceGraph interference,
            boolean minimizeRegisters) {
        super(ssaMeth, interference);

        ssaRegsMapped = new BitSet(ssaMeth.getRegCount());

        mapper = new InterferenceRegisterMapper(
                interference, ssaMeth.getRegCount());

        this.minimizeRegisters = minimizeRegisters;


        paramRangeEnd = ssaMeth.getParamWidth();

        reservedRopRegs = new BitSet(paramRangeEnd * 2);
        reservedRopRegs.set(0, paramRangeEnd);
        usedRopRegs = new BitSet(paramRangeEnd * 2);
        localVariables = new TreeMap<mod.agus.jcoderz.dx.rop.code.LocalItem, ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec>>();
        moveResultPseudoInsns = new ArrayList<NormalSsaInsn>();
        invokeRangeInsns = new ArrayList<NormalSsaInsn>();
        phiInsns = new ArrayList<PhiInsn>();
    }

    @Override
    public boolean wantsParamsMovedHigh() {
        return true;
    }

    @Override
    public RegisterMapper allocateRegisters() {

        analyzeInstructions();

        if (DEBUG) {
            printLocalVars();
        }

        if (DEBUG) System.out.println("--->Mapping local-associated params");
        handleLocalAssociatedParams();

        if (DEBUG) System.out.println("--->Mapping other params");
        handleUnassociatedParameters();

        if (DEBUG) System.out.println("--->Mapping invoke-range");
        handleInvokeRangeInsns();

        if (DEBUG) {
            System.out.println("--->Mapping local-associated non-params");
        }
        handleLocalAssociatedOther();

        if (DEBUG) System.out.println("--->Mapping check-cast results");
        handleCheckCastResults();

        if (DEBUG) System.out.println("--->Mapping phis");
        handlePhiInsns();

        if (DEBUG) System.out.println("--->Mapping others");
        handleNormalUnassociated();

        return mapper;
    }

    private void printLocalVars() {
        System.out.println("Printing local vars");
        for (Map.Entry<mod.agus.jcoderz.dx.rop.code.LocalItem, ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec>> e :
                localVariables.entrySet()) {
            StringBuilder regs = new StringBuilder();

            regs.append('{');
            regs.append(' ');
            for (mod.agus.jcoderz.dx.rop.code.RegisterSpec reg : e.getValue()) {
                regs.append('v');
                regs.append(reg.getReg());
                regs.append(' ');
            }
            regs.append('}');
            System.out.printf("Local: %s Registers: %s\n", e.getKey(), regs);
        }
    }

    private void handleLocalAssociatedParams() {
        for (ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> ssaRegs : localVariables.values()) {
            int sz = ssaRegs.size();
            int paramIndex = -1;
            int paramCategory = 0;


            for (int i = 0; i < sz; i++) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaSpec = ssaRegs.get(i);
                int ssaReg = ssaSpec.getReg();

                paramIndex = getParameterIndexForReg(ssaReg);

                if (paramIndex >= 0) {
                    paramCategory = ssaSpec.getCategory();
                    addMapping(ssaSpec, paramIndex);
                    break;
                }
            }

            if (paramIndex < 0) {

                continue;
            }


            tryMapRegs(ssaRegs, paramIndex, paramCategory, true);
        }
    }

    private int getParameterIndexForReg(int ssaReg) {
        SsaInsn defInsn = ssaMeth.getDefinitionForRegister(ssaReg);
        if (defInsn == null) {
            return -1;
        }

        Rop opcode = defInsn.getOpcode();


        if (opcode != null && opcode.getOpcode() == mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_PARAM) {
            mod.agus.jcoderz.dx.rop.code.CstInsn origInsn = (CstInsn) defInsn.getOriginalRopInsn();
            return  ((CstInteger) origInsn.getConstant()).getValue();
        }

        return -1;
    }

    private void handleLocalAssociatedOther() {
        for (ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> specs : localVariables.values()) {
            int ropReg = paramRangeEnd;

            boolean done = false;
            do {
                int maxCategory = 1;


                int sz = specs.size();
                for (int i = 0; i < sz; i++) {
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaSpec = specs.get(i);
                    int category = ssaSpec.getCategory();
                    if (!ssaRegsMapped.get(ssaSpec.getReg())
                            && category > maxCategory) {
                        maxCategory = category;
                    }
                }

                ropReg = findRopRegForLocal(ropReg, maxCategory);
                if (canMapRegs(specs, ropReg)) {
                    done = tryMapRegs(specs, ropReg, maxCategory, true);
                }


                ropReg++;
            } while (!done);
        }
    }

    private boolean tryMapRegs(
            ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> specs, int ropReg,
            int maxAllowedCategory, boolean markReserved) {
        boolean remaining = false;
        for (mod.agus.jcoderz.dx.rop.code.RegisterSpec spec : specs) {
            if (ssaRegsMapped.get(spec.getReg())) {
                continue;
            }

            boolean succeeded;
            succeeded = tryMapReg(spec, ropReg, maxAllowedCategory);
            remaining = !succeeded || remaining;
            if (succeeded && markReserved) {


                markReserved(ropReg, spec.getCategory());
            }
        }
        return !remaining;
    }

    private boolean tryMapReg(mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaSpec, int ropReg,
                              int maxAllowedCategory) {
        if (ssaSpec.getCategory() <= maxAllowedCategory
                && !ssaRegsMapped.get(ssaSpec.getReg())
                && canMapReg(ssaSpec, ropReg)) {
            addMapping(ssaSpec, ropReg);
            return true;
        }

        return false;
    }

    private void markReserved(int ropReg, int category) {
        reservedRopRegs.set(ropReg, ropReg + category, true);
    }

    private boolean rangeContainsReserved(int ropRangeStart, int width) {
        for (int i = ropRangeStart; i < (ropRangeStart + width); i++) {
            if (reservedRopRegs.get(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isThisPointerReg(int startReg) {

        return startReg == 0 && !ssaMeth.isStatic();
    }

    private Alignment getAlignment(int regCategory) {
      Alignment alignment = Alignment.UNSPECIFIED;

      if (DexOptions.ALIGN_64BIT_REGS_SUPPORT && regCategory == 2) {
        if (isEven(paramRangeEnd)) {
          alignment = Alignment.EVEN;
        } else {
          alignment = Alignment.ODD;
        }
      }

      return alignment;
    }

    private int findNextUnreservedRopReg(int startReg, int regCategory) {
      return findNextUnreservedRopReg(startReg, regCategory, getAlignment(regCategory));
    }

    private int findNextUnreservedRopReg(int startReg, int width, Alignment alignment) {
      int reg = alignment.nextClearBit(reservedRopRegs, startReg);

      while (true) {
        int i = 1;

        while (i < width && !reservedRopRegs.get(reg + i)) {
          i++;
        }

        if (i == width) {
          return reg;
        }

        reg = alignment.nextClearBit(reservedRopRegs, reg + i);
      }
    }

    private int findRopRegForLocal(int startReg, int category) {
      Alignment alignment = getAlignment(category);
      int reg = alignment.nextClearBit(usedRopRegs, startReg);

      while (true) {
        int i = 1;

        while (i < category && !usedRopRegs.get(reg + i)) {
          i++;
        }

        if (i == category) {
          return reg;
        }

        reg = alignment.nextClearBit(usedRopRegs, reg + i);
      }
    }

    private void handleUnassociatedParameters() {
        int szSsaRegs = ssaMeth.getRegCount();

        for (int ssaReg = 0; ssaReg < szSsaRegs; ssaReg++) {
            if (ssaRegsMapped.get(ssaReg)) {

                continue;
            }

            int paramIndex = getParameterIndexForReg(ssaReg);

            mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaSpec = getDefinitionSpecForSsaReg(ssaReg);
            if (paramIndex >= 0) {
                addMapping(ssaSpec, paramIndex);
            }
        }
    }

    private void handleInvokeRangeInsns() {
        for (NormalSsaInsn insn : invokeRangeInsns) {
            adjustAndMapSourceRangeRange(insn);
        }
    }

    private void handleCheckCastResults() {
        for (NormalSsaInsn insn : moveResultPseudoInsns) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec moveRegSpec = insn.getResult();
            int moveReg = moveRegSpec.getReg();
            BitSet predBlocks = insn.getBlock().getPredecessors();


            if (predBlocks.cardinality() != 1) {
                continue;
            }

            SsaBasicBlock predBlock =
                    ssaMeth.getBlocks().get(predBlocks.nextSetBit(0));
            ArrayList<SsaInsn> insnList = predBlock.getInsns();

            SsaInsn checkCastInsn = insnList.get(insnList.size() - 1);
            if (checkCastInsn.getOpcode().getOpcode() != mod.agus.jcoderz.dx.rop.code.RegOps.CHECK_CAST) {
                continue;
            }

            mod.agus.jcoderz.dx.rop.code.RegisterSpec checkRegSpec = checkCastInsn.getSources().get(0);
            int checkReg = checkRegSpec.getReg();

            int category = checkRegSpec.getCategory();
            boolean moveMapped = ssaRegsMapped.get(moveReg);
            boolean checkMapped = ssaRegsMapped.get(checkReg);
            if (moveMapped & !checkMapped) {
                int moveRopReg = mapper.oldToNew(moveReg);
                checkMapped = tryMapReg(checkRegSpec, moveRopReg, category);
            }
            if (checkMapped & !moveMapped) {
                int checkRopReg = mapper.oldToNew(checkReg);
                moveMapped = tryMapReg(moveRegSpec, checkRopReg, category);
            }


            if (!moveMapped || !checkMapped) {
                int ropReg = findNextUnreservedRopReg(paramRangeEnd, category);
                ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> ssaRegs =
                    new ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec>(2);
                ssaRegs.add(moveRegSpec);
                ssaRegs.add(checkRegSpec);

                while (!tryMapRegs(ssaRegs, ropReg, category, false)) {
                    ropReg = findNextUnreservedRopReg(ropReg + 1, category);
                }
            }

            boolean hasExceptionHandlers =
                checkCastInsn.getOriginalRopInsn().getCatches().size() != 0;
            int moveRopReg = mapper.oldToNew(moveReg);
            int checkRopReg = mapper.oldToNew(checkReg);
            if (moveRopReg != checkRopReg && !hasExceptionHandlers) {
                ((NormalSsaInsn) checkCastInsn).changeOneSource(0,
                        insertMoveBefore(checkCastInsn, checkRegSpec));
                addMapping(checkCastInsn.getSources().get(0), moveRopReg);
            }
        }
    }

    private void handlePhiInsns() {
        for (PhiInsn insn : phiInsns) {
            processPhiInsn(insn);
        }
    }

    private void handleNormalUnassociated() {
        int szSsaRegs = ssaMeth.getRegCount();

        for (int ssaReg = 0; ssaReg < szSsaRegs; ssaReg++) {
            if (ssaRegsMapped.get(ssaReg)) {

                continue;
            }

            mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaSpec = getDefinitionSpecForSsaReg(ssaReg);

            if (ssaSpec == null) continue;

            int category = ssaSpec.getCategory();

            int ropReg = findNextUnreservedRopReg(paramRangeEnd, category);
            while (!canMapReg(ssaSpec, ropReg)) {
                ropReg = findNextUnreservedRopReg(ropReg + 1, category);
            }

            addMapping(ssaSpec, ropReg);
        }
    }

    private boolean canMapRegs(ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> specs, int ropReg) {
        for (mod.agus.jcoderz.dx.rop.code.RegisterSpec spec : specs) {
            if (ssaRegsMapped.get(spec.getReg())) continue;
            if (!canMapReg(spec, ropReg)) return false;
        }
        return true;
    }

    private boolean canMapReg(mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaSpec, int ropReg) {
        int category = ssaSpec.getCategory();
        return !(spansParamRange(ropReg, category)
                || mapper.interferes(ssaSpec, ropReg));
    }

    private boolean spansParamRange(int ssaReg, int category) {
        return ((ssaReg < paramRangeEnd)
                && ((ssaReg + category) > paramRangeEnd));
    }

    private void analyzeInstructions() {
        ssaMeth.forEachInsn(new SsaInsn.Visitor() {
            @Override
            public void visitMoveInsn(NormalSsaInsn insn) {
                processInsn(insn);
            }

            @Override
            public void visitPhiInsn(PhiInsn insn) {
                processInsn(insn);
            }

            @Override
            public void visitNonMoveInsn(NormalSsaInsn insn) {
                processInsn(insn);
            }

            private void processInsn(SsaInsn insn) {
                mod.agus.jcoderz.dx.rop.code.RegisterSpec assignment;
                assignment = insn.getLocalAssignment();

                if (assignment != null) {
                    mod.agus.jcoderz.dx.rop.code.LocalItem local = assignment.getLocalItem();

                    ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> regList
                        = localVariables.get(local);

                    if (regList == null) {
                        regList = new ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec>();
                        localVariables.put(local, regList);
                    }

                    regList.add(assignment);
                }

                if (insn instanceof NormalSsaInsn) {
                    if (insn.getOpcode().getOpcode() ==
                            RegOps.MOVE_RESULT_PSEUDO) {
                        moveResultPseudoInsns.add((NormalSsaInsn) insn);
                    } else if (Optimizer.getAdvice().requiresSourcesInOrder(
                            insn.getOriginalRopInsn().getOpcode(),
                            insn.getSources())) {
                        invokeRangeInsns.add((NormalSsaInsn) insn);
                    }
                } else if (insn instanceof PhiInsn) {
                    phiInsns.add((PhiInsn) insn);
                }

            }
        });
    }

    private void addMapping(mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaSpec, int ropReg) {
        int ssaReg = ssaSpec.getReg();


        if (ssaRegsMapped.get(ssaReg) || !canMapReg(ssaSpec, ropReg)) {
            throw new RuntimeException(
                    "attempt to add invalid register mapping");
        }

        if (DEBUG) {
            System.out.printf("Add mapping s%d -> v%d c:%d\n",
                    ssaSpec.getReg(), ropReg, ssaSpec.getCategory());
        }

        int category = ssaSpec.getCategory();
        mapper.addMapping(ssaSpec.getReg(), ropReg, category);
        ssaRegsMapped.set(ssaReg);
        usedRopRegs.set(ropReg, ropReg + category);
    }


    private void adjustAndMapSourceRangeRange(NormalSsaInsn insn) {
        int newRegStart = findRangeAndAdjust(insn);

        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = insn.getSources();
        int szSources = sources.size();
        int nextRopReg = newRegStart;

        for (int i = 0; i < szSources; i++) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec source = sources.get(i);
            int sourceReg = source.getReg();
            int category = source.getCategory();
            int curRopReg = nextRopReg;
            nextRopReg += category;

            if (ssaRegsMapped.get(sourceReg)) {
                continue;
            }

            mod.agus.jcoderz.dx.rop.code.LocalItem localItem = getLocalItemForReg(sourceReg);
            addMapping(source, curRopReg);

            if (localItem != null) {
                markReserved(curRopReg, category);
                ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> similarRegisters
                        = localVariables.get(localItem);

                int szSimilar = similarRegisters.size();

                for (int j = 0; j < szSimilar; j++) {
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec similarSpec = similarRegisters.get(j);
                    int similarReg = similarSpec.getReg();


                    if (-1 != sources.indexOfRegister(similarReg)) {
                        continue;
                    }


                    tryMapReg(similarSpec, curRopReg, category);
                }
            }
        }
    }

    private int findRangeAndAdjust(NormalSsaInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = insn.getSources();
        int szSources = sources.size();

        int categoriesForIndex[] = new int[szSources];
        int rangeLength = 0;


        for (int i = 0; i < szSources; i++) {
            int category = sources.get(i).getCategory();
            categoriesForIndex[i] = category;
            rangeLength += categoriesForIndex[i];
        }


        int maxScore = Integer.MIN_VALUE;

        int resultRangeStart = -1;

        BitSet resultMovesRequired = null;

        int rangeStartOffset = 0;
        for (int i = 0; i < szSources; i++) {
            int ssaCenterReg = sources.get(i).getReg();

            if (i != 0) {
                rangeStartOffset -= categoriesForIndex[i - 1];
            }
            if (!ssaRegsMapped.get(ssaCenterReg)) {
                continue;
            }

            int rangeStart = mapper.oldToNew(ssaCenterReg) + rangeStartOffset;

            if (rangeStart < 0 || spansParamRange(rangeStart, rangeLength)) {
                continue;
            }

            BitSet curMovesRequired = new BitSet(szSources);

            int fitWidth
                    = fitPlanForRange(rangeStart, insn, categoriesForIndex,
                    curMovesRequired);

            if (fitWidth < 0) {
                continue;
            }

            int score = fitWidth - curMovesRequired.cardinality();

            if (score > maxScore) {
                maxScore = score;
                resultRangeStart = rangeStart;
                resultMovesRequired = curMovesRequired;
            }

            if (fitWidth == rangeLength) {

                break;
            }
        }


        if (resultRangeStart == -1) {
            resultMovesRequired = new BitSet(szSources);

            resultRangeStart = findAnyFittingRange(insn, rangeLength,
                    categoriesForIndex, resultMovesRequired);
        }


        for (int i = resultMovesRequired.nextSetBit(0); i >= 0;
             i = resultMovesRequired.nextSetBit(i+1)) {
            insn.changeOneSource(i, insertMoveBefore(insn, sources.get(i)));
        }

        return resultRangeStart;
    }

    private int findAnyFittingRange(NormalSsaInsn insn, int rangeLength,
            int[] categoriesForIndex, BitSet outMovesRequired) {
        Alignment alignment = Alignment.UNSPECIFIED;

        if (DexOptions.ALIGN_64BIT_REGS_SUPPORT) {
          int regNumber = 0;
          int p64bitsAligned = 0;
          int p64bitsNotAligned = 0;
          for (int category : categoriesForIndex) {
            if (category == 2) {
              if (isEven(regNumber)) {
                p64bitsAligned++;
              } else {
                p64bitsNotAligned++;
              }
              regNumber += 2;
            } else {
              regNumber += 1;
            }
          }

          if (p64bitsNotAligned > p64bitsAligned) {
            if (isEven(paramRangeEnd)) {
              alignment = Alignment.ODD;
            } else {
              alignment = Alignment.EVEN;
            }
          } else if (p64bitsAligned > 0) {
            if (isEven(paramRangeEnd)) {
              alignment = Alignment.EVEN;
            } else {
              alignment = Alignment.ODD;
            }
          }
        }

        int rangeStart = paramRangeEnd;
        while (true) {
          rangeStart = findNextUnreservedRopReg(rangeStart, rangeLength, alignment);

          int fitWidth = fitPlanForRange(rangeStart, insn, categoriesForIndex, outMovesRequired);

          if (fitWidth >= 0) {
            break;
          }
          rangeStart++;
          outMovesRequired.clear();
        }

        return rangeStart;
    }

    private int fitPlanForRange(int ropReg, NormalSsaInsn insn,
            int[] categoriesForIndex, BitSet outMovesRequired) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList sources = insn.getSources();
        int szSources = sources.size();
        int fitWidth = 0;
        IntSet liveOut = insn.getBlock().getLiveOutRegs();
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList liveOutSpecs = ssaSetToSpecs(liveOut);


        BitSet seen = new BitSet(ssaMeth.getRegCount());

        for (int i = 0; i < szSources ; i++) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec ssaSpec = sources.get(i);
            int ssaReg = ssaSpec.getReg();
            int category = categoriesForIndex[i];

            if (i != 0) {
                ropReg += categoriesForIndex[i-1];
            }

            if (ssaRegsMapped.get(ssaReg)
                    && mapper.oldToNew(ssaReg) == ropReg) {

                fitWidth += category;
            } else if (rangeContainsReserved(ropReg, category)) {
                fitWidth = -1;
                break;
            } else if (!ssaRegsMapped.get(ssaReg)
                    && canMapReg(ssaSpec, ropReg)
                    && !seen.get(ssaReg)) {

                fitWidth += category;
            } else if (!mapper.areAnyPinned(liveOutSpecs, ropReg, category)
                    && !mapper.areAnyPinned(sources, ropReg, category)) {

                outMovesRequired.set(i);
            } else {
                fitWidth = -1;
                break;
            }

            seen.set(ssaReg);
        }
        return fitWidth;
    }

    mod.agus.jcoderz.dx.rop.code.RegisterSpecList ssaSetToSpecs(IntSet ssaSet) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpecList result = new mod.agus.jcoderz.dx.rop.code.RegisterSpecList(ssaSet.elements());

        IntIterator iter = ssaSet.iterator();

        int i = 0;
        while (iter.hasNext()) {
            result.set(i++, getDefinitionSpecForSsaReg(iter.next()));
        }

        return result;
    }

    private mod.agus.jcoderz.dx.rop.code.LocalItem getLocalItemForReg(int ssaReg) {
        for (Map.Entry<LocalItem, ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec>> entry :
                 localVariables.entrySet()) {
            for (mod.agus.jcoderz.dx.rop.code.RegisterSpec spec : entry.getValue()) {
                if (spec.getReg() == ssaReg) {
                    return entry.getKey();
                }
            }
        }

        return null;
    }

    private void processPhiInsn(PhiInsn insn) {
        mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();
        int resultReg = result.getReg();
        int category = result.getCategory();

        RegisterSpecList sources = insn.getSources();
        int sourcesSize = sources.size();


        ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec> ssaRegs = new ArrayList<mod.agus.jcoderz.dx.rop.code.RegisterSpec>();


        Multiset mapSet = new Multiset(sourcesSize + 1);

        if (ssaRegsMapped.get(resultReg)) {
            mapSet.add(mapper.oldToNew(resultReg));
        } else {
            ssaRegs.add(result);
        }

        for (int i = 0; i < sourcesSize; i++) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec source = sources.get(i);
            SsaInsn def = ssaMeth.getDefinitionForRegister(source.getReg());
            RegisterSpec sourceDef = def.getResult();
            int sourceReg = sourceDef.getReg();

            if (ssaRegsMapped.get(sourceReg)) {
                mapSet.add(mapper.oldToNew(sourceReg));
            } else {
                ssaRegs.add(sourceDef);
            }
        }


        for (int i = 0; i < mapSet.getSize(); i++) {
            int maxReg = mapSet.getAndRemoveHighestCount();
            tryMapRegs(ssaRegs, maxReg, category, false);
        }


        int mapReg = findNextUnreservedRopReg(paramRangeEnd, category);
        while (!tryMapRegs(ssaRegs, mapReg, category, false)) {
            mapReg = findNextUnreservedRopReg(mapReg + 1, category);
        }
    }

    private static boolean isEven(int regNumger) {
      return ((regNumger & 1) == 0);
    }


    private static class Multiset {
        private final int[] reg;
        private final int[] count;
        private int size;

        public Multiset(int maxSize) {
            reg = new int[maxSize];
            count = new int[maxSize];
            size = 0;
        }

        public void add(int element) {
            for (int i = 0; i < size; i++) {
                if (reg[i] == element) {
                    count[i]++;
                    return;
                }
            }

            reg[size] = element;
            count[size] = 1;
            size++;
        }

        public int getAndRemoveHighestCount() {
            int maxIndex = -1;
            int maxReg = -1;
            int maxCount = 0;

            for (int i = 0; i < size; i++) {
                if (maxCount < count[i]) {
                    maxIndex = i;
                    maxReg = reg[i];
                    maxCount = count[i];
                }
            }

            count[maxIndex] = 0;
            return maxReg;
        }

        public int getSize() {
            return size;
        }
    }
}
