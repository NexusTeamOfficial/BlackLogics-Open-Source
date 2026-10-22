
package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.io.Opcodes;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstMemberRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.ssa.BasicRegisterMapper;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;

public final class OutputFinisher {
    private final mod.agus.jcoderz.dx.dex.DexOptions dexOptions;

    private final int unreservedRegCount;

    private ArrayList<mod.agus.jcoderz.dx.dex.code.DalvInsn> insns;

    private boolean hasAnyPositionInfo;

    private boolean hasAnyLocalInfo;

    private int reservedCount;

    private int reservedParameterCount;

    private final int paramSize;

    public OutputFinisher(DexOptions dexOptions, int initialCapacity, int regCount, int paramSize) {
        this.dexOptions = dexOptions;
        this.unreservedRegCount = regCount;
        this.insns = new ArrayList<mod.agus.jcoderz.dx.dex.code.DalvInsn>(initialCapacity);
        this.reservedCount = -1;
        this.hasAnyPositionInfo = false;
        this.hasAnyLocalInfo = false;
        this.paramSize = paramSize;
    }

    public boolean hasAnyPositionInfo() {
        return hasAnyPositionInfo;
    }

    public boolean hasAnyLocalInfo() {
        return hasAnyLocalInfo;
    }

    private static boolean hasLocalInfo(mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        if (insn instanceof mod.agus.jcoderz.dx.dex.code.LocalSnapshot) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpecSet specs = ((mod.agus.jcoderz.dx.dex.code.LocalSnapshot) insn).getLocals();
            int size = specs.size();
            for (int i = 0; i < size; i++) {
                if (hasLocalInfo(specs.get(i))) {
                    return true;
                }
            }
        } else if (insn instanceof LocalStart) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec spec = ((LocalStart) insn).getLocal();
            if (hasLocalInfo(spec)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasLocalInfo(mod.agus.jcoderz.dx.rop.code.RegisterSpec spec) {
        return (spec != null)
            && (spec.getLocalItem().getName() != null);
    }

    public HashSet<mod.agus.jcoderz.dx.rop.cst.Constant> getAllConstants() {
        HashSet<mod.agus.jcoderz.dx.rop.cst.Constant> result = new HashSet<mod.agus.jcoderz.dx.rop.cst.Constant>(20);

        for (mod.agus.jcoderz.dx.dex.code.DalvInsn insn : insns) {
            addConstants(result, insn);
        }

        return result;
    }

    private static void addConstants(HashSet<mod.agus.jcoderz.dx.rop.cst.Constant> result,
            mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        if (insn instanceof mod.agus.jcoderz.dx.dex.code.CstInsn) {
            mod.agus.jcoderz.dx.rop.cst.Constant cst = ((mod.agus.jcoderz.dx.dex.code.CstInsn) insn).getConstant();
            result.add(cst);
        } else if (insn instanceof MultiCstInsn) {
            MultiCstInsn m = (MultiCstInsn) insn;
            for (int i = 0; i < m.getNumberOfConstants(); i++) {
                result.add(m.getConstant(i));
            }
        } else if (insn instanceof mod.agus.jcoderz.dx.dex.code.LocalSnapshot) {
            RegisterSpecSet specs = ((LocalSnapshot) insn).getLocals();
            int size = specs.size();
            for (int i = 0; i < size; i++) {
                addConstants(result, specs.get(i));
            }
        } else if (insn instanceof LocalStart) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec spec = ((LocalStart) insn).getLocal();
            addConstants(result, spec);
        }
    }

    private static void addConstants(HashSet<mod.agus.jcoderz.dx.rop.cst.Constant> result,
            mod.agus.jcoderz.dx.rop.code.RegisterSpec spec) {
        if (spec == null) {
            return;
        }

        LocalItem local = spec.getLocalItem();
        mod.agus.jcoderz.dx.rop.cst.CstString name = local.getName();
        CstString signature = local.getSignature();
        mod.agus.jcoderz.dx.rop.type.Type type = spec.getType();

        if (type != mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
            result.add(mod.agus.jcoderz.dx.rop.cst.CstType.intern(type));
        } else {
            result.add(mod.agus.jcoderz.dx.rop.cst.CstType.intern(Type.OBJECT));
        }

        if (name != null) {
            result.add(name);
        }

        if (signature != null) {
            result.add(signature);
        }
    }

    public void add(mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        insns.add(insn);
        updateInfo(insn);
    }

    public void insert(int at, mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        insns.add(at, insn);
        updateInfo(insn);
    }

    public mod.agus.jcoderz.dx.dex.code.DalvInsn get(int at) {
        return insns.get(at);
    }

    public int size() {
        return insns.size();
    }

    private void updateInfo(mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        if (! hasAnyPositionInfo) {
            SourcePosition pos = insn.getPosition();
            if (pos.getLine() >= 0) {
                hasAnyPositionInfo = true;
            }
        }

        if (! hasAnyLocalInfo) {
            if (hasLocalInfo(insn)) {
                hasAnyLocalInfo = true;
            }
        }
    }

    public void reverseBranch(int which, mod.agus.jcoderz.dx.dex.code.CodeAddress newTarget) {
        int size = insns.size();
        int index = size - which - 1;
        mod.agus.jcoderz.dx.dex.code.TargetInsn targetInsn;

        try {
            targetInsn = (mod.agus.jcoderz.dx.dex.code.TargetInsn) insns.get(index);
        } catch (IndexOutOfBoundsException ex) {

            throw new IllegalArgumentException("too few instructions");
        } catch (ClassCastException ex) {

            throw new IllegalArgumentException("non-reversible instruction");
        }

        insns.set(index, targetInsn.withNewTargetAndReversed(newTarget));
    }

    public void assignIndices(DalvCode.AssignIndicesCallback callback) {
        for (mod.agus.jcoderz.dx.dex.code.DalvInsn insn : insns) {
            if (insn instanceof mod.agus.jcoderz.dx.dex.code.CstInsn) {
                assignIndices((mod.agus.jcoderz.dx.dex.code.CstInsn) insn, callback);
            } else if (insn instanceof MultiCstInsn) {
                assignIndices((MultiCstInsn) insn, callback);
            }
        }
    }

    private static void assignIndices(CstInsn insn,
                                      DalvCode.AssignIndicesCallback callback) {
        mod.agus.jcoderz.dx.rop.cst.Constant cst = insn.getConstant();
        int index = callback.getIndex(cst);

        if (index >= 0) {
            insn.setIndex(index);
        }

        if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstMemberRef) {
            mod.agus.jcoderz.dx.rop.cst.CstMemberRef member = (mod.agus.jcoderz.dx.rop.cst.CstMemberRef) cst;
            mod.agus.jcoderz.dx.rop.cst.CstType definer = member.getDefiningClass();
            index = callback.getIndex(definer);

            if (index >= 0) {
                insn.setClassIndex(index);
            }
        }
    }

    private static void assignIndices(MultiCstInsn insn, DalvCode.AssignIndicesCallback callback) {
        for (int i = 0; i < insn.getNumberOfConstants(); ++i) {
            Constant cst = insn.getConstant(i);
            int index = callback.getIndex(cst);
            insn.setIndex(i, index);

            if (cst instanceof mod.agus.jcoderz.dx.rop.cst.CstMemberRef) {
                mod.agus.jcoderz.dx.rop.cst.CstMemberRef member = (CstMemberRef) cst;
                CstType definer = member.getDefiningClass();
                index = callback.getIndex(definer);
                insn.setClassIndex(index);
            }
        }
    }

    public DalvInsnList finishProcessingAndGetList() {
        if (reservedCount >= 0) {
            throw new UnsupportedOperationException("already processed");
        }

        mod.agus.jcoderz.dx.dex.code.Dop[] opcodes = makeOpcodesArray();
        reserveRegisters(opcodes);
        if (dexOptions.ALIGN_64BIT_REGS_IN_OUTPUT_FINISHER) {
          align64bits(opcodes);
        }
        massageInstructions(opcodes);
        assignAddressesAndFixBranches();

        return DalvInsnList.makeImmutable(insns, reservedCount + unreservedRegCount
            + reservedParameterCount);
    }

    private mod.agus.jcoderz.dx.dex.code.Dop[] makeOpcodesArray() {
        int size = insns.size();
        mod.agus.jcoderz.dx.dex.code.Dop[] result = new mod.agus.jcoderz.dx.dex.code.Dop[size];

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.dex.code.DalvInsn insn = insns.get(i);
            result[i] = insn.getOpcode();
        }

        return result;
    }

    private boolean reserveRegisters(mod.agus.jcoderz.dx.dex.code.Dop[] opcodes) {
        boolean reservedCountExpanded = false;
        int oldReservedCount = (reservedCount < 0) ? 0 : reservedCount;

        for (;;) {
            int newReservedCount = calculateReservedCount(opcodes);
            if (oldReservedCount >= newReservedCount) {
                break;
            }

            reservedCountExpanded = true;

            int reservedDifference = newReservedCount - oldReservedCount;
            int size = insns.size();

            for (int i = 0; i < size; i++) {
                mod.agus.jcoderz.dx.dex.code.DalvInsn insn = insns.get(i);
                if (!(insn instanceof mod.agus.jcoderz.dx.dex.code.CodeAddress)) {
                    insns.set(i, insn.withRegisterOffset(reservedDifference));
                }
            }

            oldReservedCount = newReservedCount;
        }

        reservedCount = oldReservedCount;

        return reservedCountExpanded;
    }

    private int calculateReservedCount(mod.agus.jcoderz.dx.dex.code.Dop[] opcodes) {
        int size = insns.size();

        int newReservedCount = reservedCount;

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.dex.code.DalvInsn insn = insns.get(i);
            mod.agus.jcoderz.dx.dex.code.Dop originalOpcode = opcodes[i];
            mod.agus.jcoderz.dx.dex.code.Dop newOpcode = findOpcodeForInsn(insn, originalOpcode);

            if (newOpcode == null) {
                mod.agus.jcoderz.dx.dex.code.Dop expandedOp = findExpandedOpcodeForInsn(insn);
                BitSet compatRegs = expandedOp.getFormat().compatibleRegs(insn);
                int reserve = insn.getMinimumRegisterRequirement(compatRegs);
                if (reserve > newReservedCount) {
                    newReservedCount = reserve;
                }
            } else if (originalOpcode == newOpcode) {
                continue;
            }

            opcodes[i] = newOpcode;
        }

        return newReservedCount;
    }

    private mod.agus.jcoderz.dx.dex.code.Dop findOpcodeForInsn(mod.agus.jcoderz.dx.dex.code.DalvInsn insn, mod.agus.jcoderz.dx.dex.code.Dop guess) {

        while (guess != null) {
            if (guess.getFormat().isCompatible(insn)) {
                if (!dexOptions.forceJumbo ||
                    guess.getOpcode() != mod.agus.jcoderz.dx.io.Opcodes.CONST_STRING) {
                    break;
                }
            }

            guess = mod.agus.jcoderz.dx.dex.code.Dops.getNextOrNull(guess, dexOptions);
        }

        return guess;
    }

    private mod.agus.jcoderz.dx.dex.code.Dop findExpandedOpcodeForInsn(mod.agus.jcoderz.dx.dex.code.DalvInsn insn) {
        mod.agus.jcoderz.dx.dex.code.Dop result = findOpcodeForInsn(insn.getLowRegVersion(), insn.getOpcode());
        if (result == null) {
            throw new DexException("No expanded opcode for " + insn);
        }
        return result;
    }

    private void massageInstructions(mod.agus.jcoderz.dx.dex.code.Dop[] opcodes) {
        if (reservedCount == 0) {
            int size = insns.size();

            for (int i = 0; i < size; i++) {
                mod.agus.jcoderz.dx.dex.code.DalvInsn insn = insns.get(i);
                mod.agus.jcoderz.dx.dex.code.Dop originalOpcode = insn.getOpcode();
                mod.agus.jcoderz.dx.dex.code.Dop currentOpcode = opcodes[i];

                if (originalOpcode != currentOpcode) {
                    insns.set(i, insn.withOpcode(currentOpcode));
                }
            }
        } else {
            insns = performExpansion(opcodes);
        }
    }

    private ArrayList<mod.agus.jcoderz.dx.dex.code.DalvInsn> performExpansion(mod.agus.jcoderz.dx.dex.code.Dop[] opcodes) {
        int size = insns.size();
        ArrayList<mod.agus.jcoderz.dx.dex.code.DalvInsn> result = new ArrayList<mod.agus.jcoderz.dx.dex.code.DalvInsn>(size * 2);

        ArrayList<mod.agus.jcoderz.dx.dex.code.CodeAddress> closelyBoundAddresses = new ArrayList<mod.agus.jcoderz.dx.dex.code.CodeAddress>();

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.dex.code.DalvInsn insn = insns.get(i);
            mod.agus.jcoderz.dx.dex.code.Dop originalOpcode = insn.getOpcode();
            mod.agus.jcoderz.dx.dex.code.Dop currentOpcode = opcodes[i];
            mod.agus.jcoderz.dx.dex.code.DalvInsn prefix;
            mod.agus.jcoderz.dx.dex.code.DalvInsn suffix;

            if (currentOpcode != null) {

                prefix = null;
                suffix = null;
            } else {

                currentOpcode = findExpandedOpcodeForInsn(insn);
                BitSet compatRegs =
                    currentOpcode.getFormat().compatibleRegs(insn);
                prefix = insn.expandedPrefix(compatRegs);
                suffix = insn.expandedSuffix(compatRegs);


                insn = insn.expandedVersion(compatRegs);
            }

            if (insn instanceof mod.agus.jcoderz.dx.dex.code.CodeAddress) {



                if (((mod.agus.jcoderz.dx.dex.code.CodeAddress) insn).getBindsClosely()) {
                    closelyBoundAddresses.add((mod.agus.jcoderz.dx.dex.code.CodeAddress)insn);
                    continue;
                }
            }

            if (prefix != null) {
                result.add(prefix);
            }


            if (!(insn instanceof ZeroSizeInsn) && closelyBoundAddresses.size() > 0) {
                for (mod.agus.jcoderz.dx.dex.code.CodeAddress codeAddress: closelyBoundAddresses) {
                    result.add(codeAddress);
                }
                closelyBoundAddresses.clear();
            }

            if (currentOpcode != originalOpcode) {
                insn = insn.withOpcode(currentOpcode);
            }
            result.add(insn);

            if (suffix != null) {
                result.add(suffix);
            }
        }

        return result;
    }

    private void assignAddressesAndFixBranches() {
        for (;;) {
            assignAddresses();
            if (!fixBranches()) {
                break;
            }
        }
    }

    private void assignAddresses() {
        int address = 0;
        int size = insns.size();

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.dex.code.DalvInsn insn = insns.get(i);
            insn.setAddress(address);
            address += insn.codeSize();
        }
    }

    private boolean fixBranches() {
        int size = insns.size();
        boolean anyFixed = false;

        for (int i = 0; i < size; i++) {
            mod.agus.jcoderz.dx.dex.code.DalvInsn insn = insns.get(i);
            if (!(insn instanceof mod.agus.jcoderz.dx.dex.code.TargetInsn)) {

                continue;
            }

            mod.agus.jcoderz.dx.dex.code.Dop opcode = insn.getOpcode();
            mod.agus.jcoderz.dx.dex.code.TargetInsn target = (mod.agus.jcoderz.dx.dex.code.TargetInsn) insn;

            if (opcode.getFormat().branchFits(target)) {
                continue;
            }

            if (opcode.getFamily() == Opcodes.GOTO) {

                opcode = findOpcodeForInsn(insn, opcode);
                if (opcode == null) {
                    throw new UnsupportedOperationException("method too long");
                }
                insns.set(i, insn.withOpcode(opcode));
            } else {
                mod.agus.jcoderz.dx.dex.code.CodeAddress newTarget;
                try {
                    newTarget = (mod.agus.jcoderz.dx.dex.code.CodeAddress) insns.get(i + 1);
                } catch (IndexOutOfBoundsException ex) {

                    throw new IllegalStateException(
                            "unpaired TargetInsn (dangling)");
                } catch (ClassCastException ex) {

                    throw new IllegalStateException("unpaired TargetInsn");
                }
                mod.agus.jcoderz.dx.dex.code.TargetInsn gotoInsn =
                    new TargetInsn(Dops.GOTO, target.getPosition(),
                            mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY, target.getTarget());
                insns.set(i, gotoInsn);
                insns.add(i, target.withNewTargetAndReversed(newTarget));
                size++;
                i++;
            }

            anyFixed = true;
        }

        return anyFixed;
    }

    private void align64bits(Dop[] opcodes) {
      while (true) {
        int notAligned64bitRegAccess = 0;
        int aligned64bitRegAccess = 0;
        int notAligned64bitParamAccess = 0;
        int aligned64bitParamAccess = 0;
        int lastParameter = unreservedRegCount + reservedCount + reservedParameterCount;
        int firstParameter = lastParameter - paramSize;


        for (mod.agus.jcoderz.dx.dex.code.DalvInsn insn : insns) {
          RegisterSpecList regs = insn.getRegisters();
          for (int usedRegIdx = 0; usedRegIdx < regs.size(); usedRegIdx++) {
            RegisterSpec reg = regs.get(usedRegIdx);
            if (reg.isCategory2()) {
              boolean isParameter = reg.getReg() >= firstParameter;
              if (reg.isEvenRegister()) {
                if (isParameter) {
                  aligned64bitParamAccess++;
                } else {
                  aligned64bitRegAccess++;
                }
              } else {
                if (isParameter) {
                  notAligned64bitParamAccess++;
                } else {
                  notAligned64bitRegAccess++;
                }
              }
            }
          }
        }

        if (notAligned64bitParamAccess > aligned64bitParamAccess
            && notAligned64bitRegAccess > aligned64bitRegAccess) {
          addReservedRegisters(1);
        } else if (notAligned64bitParamAccess > aligned64bitParamAccess) {
          addReservedParameters(1);
        } else if (notAligned64bitRegAccess > aligned64bitRegAccess) {
          addReservedRegisters(1);




          if (paramSize != 0 && aligned64bitParamAccess > notAligned64bitParamAccess) {
            addReservedParameters(1);
          }
        } else {
          break;
        }

        if (!reserveRegisters(opcodes)) {
          break;
        }
      }
    }

    private void addReservedParameters(int delta) {
      shiftParameters(delta);
      reservedParameterCount += delta;
    }

    private void addReservedRegisters(int delta) {
      shiftAllRegisters(delta);
      reservedCount += delta;
    }

    private void shiftAllRegisters(int delta) {
      int insnSize = insns.size();

      for (int i = 0; i < insnSize; i++) {
        mod.agus.jcoderz.dx.dex.code.DalvInsn insn = insns.get(i);


        if (!(insn instanceof mod.agus.jcoderz.dx.dex.code.CodeAddress)) {
          insns.set(i, insn.withRegisterOffset(delta));
        }
      }
    }

    private void shiftParameters(int delta) {
      int insnSize = insns.size();
      int lastParameter = unreservedRegCount + reservedCount + reservedParameterCount;
      int firstParameter = lastParameter - paramSize;

      mod.agus.jcoderz.dx.ssa.BasicRegisterMapper mapper = new BasicRegisterMapper(lastParameter);
      for (int i = 0; i < lastParameter; i++) {
        if (i >= firstParameter) {
          mapper.addMapping(i, i + delta, 1);
        } else {
          mapper.addMapping(i, i, 1);
        }
      }

      for (int i = 0; i < insnSize; i++) {
        DalvInsn insn = insns.get(i);


        if (!(insn instanceof CodeAddress)) {
          insns.set(i, insn.withMapper(mapper));
        }
      }
    }
}
