
package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public final class Rops {
    public static final mod.agus.jcoderz.dx.rop.code.Rop NOP =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NOP, Type.VOID, StdTypeList.EMPTY, "nop");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE, Type.INT, StdTypeList.INT, "move-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE, Type.LONG, StdTypeList.LONG, "move-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE, Type.FLOAT, StdTypeList.FLOAT, "move-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE, Type.DOUBLE, StdTypeList.DOUBLE, "move-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE, Type.OBJECT, StdTypeList.OBJECT, "move-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_RETURN_ADDRESS =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE, Type.RETURN_ADDRESS,
                StdTypeList.RETURN_ADDRESS, "move-return-address");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_PARAM_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE_PARAM, Type.INT, StdTypeList.EMPTY,
                "move-param-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_PARAM_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE_PARAM, Type.LONG, StdTypeList.EMPTY,
                "move-param-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_PARAM_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE_PARAM, Type.FLOAT, StdTypeList.EMPTY,
                "move-param-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_PARAM_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE_PARAM, Type.DOUBLE, StdTypeList.EMPTY,
                "move-param-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MOVE_PARAM_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE_PARAM, Type.OBJECT, StdTypeList.EMPTY,
                "move-param-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONST, Type.INT, StdTypeList.EMPTY, "const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONST, Type.LONG, StdTypeList.EMPTY, "const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONST_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONST, Type.FLOAT, StdTypeList.EMPTY, "const-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONST_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONST, Type.DOUBLE, StdTypeList.EMPTY, "const-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONST_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONST, Type.OBJECT, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "const-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONST_OBJECT_NOTHROW =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONST, Type.OBJECT, StdTypeList.EMPTY,
                "const-object-nothrow");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GOTO =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GOTO, Type.VOID, StdTypeList.EMPTY, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_GOTO,
                "goto");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_EQZ_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_EQ, Type.VOID, StdTypeList.INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-eqz-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_NEZ_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_NE, Type.VOID, StdTypeList.INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-nez-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_LTZ_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_LT, Type.VOID, StdTypeList.INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-ltz-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_GEZ_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_GE, Type.VOID, StdTypeList.INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-gez-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_LEZ_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_LE, Type.VOID, StdTypeList.INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-lez-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_GTZ_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_GT, Type.VOID, StdTypeList.INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-gtz-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_EQZ_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_EQ, Type.VOID, StdTypeList.OBJECT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-eqz-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_NEZ_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_NE, Type.VOID, StdTypeList.OBJECT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-nez-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_EQ_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_EQ, Type.VOID, StdTypeList.INT_INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-eq-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_NE_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_NE, Type.VOID, StdTypeList.INT_INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-ne-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_LT_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_LT, Type.VOID, StdTypeList.INT_INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-lt-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_GE_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_GE, Type.VOID, StdTypeList.INT_INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-ge-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_LE_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_LE, Type.VOID, StdTypeList.INT_INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-le-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_GT_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_GT, Type.VOID, StdTypeList.INT_INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF,
                "if-gt-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_EQ_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_EQ, Type.VOID, StdTypeList.OBJECT_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF, "if-eq-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop IF_NE_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.IF_NE, Type.VOID, StdTypeList.OBJECT_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_IF, "if-ne-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SWITCH =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SWITCH, Type.VOID, StdTypeList.INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_SWITCH,
                "switch");

    public static final mod.agus.jcoderz.dx.rop.code.Rop ADD_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.ADD, Type.INT, StdTypeList.INT_INT, "add-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop ADD_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.ADD, Type.LONG, StdTypeList.LONG_LONG, "add-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop ADD_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.ADD, Type.FLOAT, StdTypeList.FLOAT_FLOAT, "add-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop ADD_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.ADD, Type.DOUBLE, StdTypeList.DOUBLE_DOUBLE,
                mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_NONE, "add-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SUB_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SUB, Type.INT, StdTypeList.INT_INT, "sub-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SUB_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SUB, Type.LONG, StdTypeList.LONG_LONG, "sub-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SUB_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SUB, Type.FLOAT, StdTypeList.FLOAT_FLOAT, "sub-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SUB_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SUB, Type.DOUBLE, StdTypeList.DOUBLE_DOUBLE,
                mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_NONE, "sub-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MUL_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MUL, Type.INT, StdTypeList.INT_INT, "mul-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MUL_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MUL, Type.LONG, StdTypeList.LONG_LONG, "mul-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MUL_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MUL, Type.FLOAT, StdTypeList.FLOAT_FLOAT, "mul-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MUL_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MUL, Type.DOUBLE, StdTypeList.DOUBLE_DOUBLE,
                mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_NONE, "mul-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop DIV_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.DIV, Type.INT, StdTypeList.INT_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_ArithmeticException, "div-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop DIV_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.DIV, Type.LONG, StdTypeList.LONG_LONG,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_ArithmeticException, "div-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop DIV_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.DIV, Type.FLOAT, StdTypeList.FLOAT_FLOAT, "div-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop DIV_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.DIV, Type.DOUBLE, StdTypeList.DOUBLE_DOUBLE,
                "div-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop REM_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.REM, Type.INT, StdTypeList.INT_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_ArithmeticException, "rem-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop REM_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.REM, Type.LONG, StdTypeList.LONG_LONG,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_ArithmeticException, "rem-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop REM_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.REM, Type.FLOAT, StdTypeList.FLOAT_FLOAT, "rem-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop REM_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.REM, Type.DOUBLE, StdTypeList.DOUBLE_DOUBLE,
                "rem-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEG_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEG, Type.INT, StdTypeList.INT, "neg-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEG_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEG, Type.LONG, StdTypeList.LONG, "neg-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEG_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEG, Type.FLOAT, StdTypeList.FLOAT, "neg-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEG_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEG, Type.DOUBLE, StdTypeList.DOUBLE, "neg-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AND_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AND, Type.INT, StdTypeList.INT_INT, "and-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AND_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AND, Type.LONG, StdTypeList.LONG_LONG, "and-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop OR_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.OR, Type.INT, StdTypeList.INT_INT, "or-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop OR_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.OR, Type.LONG, StdTypeList.LONG_LONG, "or-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop XOR_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.XOR, Type.INT, StdTypeList.INT_INT, "xor-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop XOR_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.XOR, Type.LONG, StdTypeList.LONG_LONG, "xor-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SHL_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SHL, Type.INT, StdTypeList.INT_INT, "shl-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SHL_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SHL, Type.LONG, StdTypeList.LONG_INT, "shl-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SHR_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SHR, Type.INT, StdTypeList.INT_INT, "shr-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SHR_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SHR, Type.LONG, StdTypeList.LONG_INT, "shr-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop USHR_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.USHR, Type.INT, StdTypeList.INT_INT, "ushr-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop USHR_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.USHR, Type.LONG, StdTypeList.LONG_INT, "ushr-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NOT_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NOT, Type.INT, StdTypeList.INT, "not-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NOT_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NOT, Type.LONG, StdTypeList.LONG, "not-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop ADD_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.ADD, Type.INT, StdTypeList.INT, "add-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop ADD_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.ADD, Type.LONG, StdTypeList.LONG, "add-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop ADD_CONST_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.ADD, Type.FLOAT, StdTypeList.FLOAT, "add-const-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop ADD_CONST_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.ADD, Type.DOUBLE, StdTypeList.DOUBLE,
                "add-const-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SUB_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SUB, Type.INT, StdTypeList.INT, "sub-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SUB_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SUB, Type.LONG, StdTypeList.LONG, "sub-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SUB_CONST_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SUB, Type.FLOAT, StdTypeList.FLOAT, "sub-const-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SUB_CONST_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SUB, Type.DOUBLE, StdTypeList.DOUBLE,
                "sub-const-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MUL_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MUL, Type.INT, StdTypeList.INT, "mul-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MUL_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MUL, Type.LONG, StdTypeList.LONG, "mul-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MUL_CONST_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MUL, Type.FLOAT, StdTypeList.FLOAT, "mul-const-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MUL_CONST_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MUL, Type.DOUBLE, StdTypeList.DOUBLE,
                "mul-const-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop DIV_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.DIV, Type.INT, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_ArithmeticException, "div-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop DIV_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.DIV, Type.LONG, StdTypeList.LONG,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_ArithmeticException, "div-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop DIV_CONST_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.DIV, Type.FLOAT, StdTypeList.FLOAT, "div-const-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop DIV_CONST_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.DIV, Type.DOUBLE, StdTypeList.DOUBLE,
                "div-const-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop REM_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.REM, Type.INT, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_ArithmeticException, "rem-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop REM_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.REM, Type.LONG, StdTypeList.LONG,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_ArithmeticException, "rem-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop REM_CONST_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.REM, Type.FLOAT, StdTypeList.FLOAT, "rem-const-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop REM_CONST_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.REM, Type.DOUBLE, StdTypeList.DOUBLE,
                "rem-const-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AND_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AND, Type.INT, StdTypeList.INT, "and-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AND_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AND, Type.LONG, StdTypeList.LONG, "and-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop OR_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.OR, Type.INT, StdTypeList.INT, "or-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop OR_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.OR, Type.LONG, StdTypeList.LONG, "or-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop XOR_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.XOR, Type.INT, StdTypeList.INT, "xor-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop XOR_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.XOR, Type.LONG, StdTypeList.LONG, "xor-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SHL_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SHL, Type.INT, StdTypeList.INT, "shl-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SHL_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SHL, Type.LONG, StdTypeList.INT, "shl-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SHR_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SHR, Type.INT, StdTypeList.INT, "shr-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop SHR_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.SHR, Type.LONG, StdTypeList.INT, "shr-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop USHR_CONST_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.USHR, Type.INT, StdTypeList.INT, "ushr-const-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop USHR_CONST_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.USHR, Type.LONG, StdTypeList.INT, "ushr-const-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CMPL_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CMPL, Type.INT, StdTypeList.LONG_LONG, "cmpl-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CMPL_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CMPL, Type.INT, StdTypeList.FLOAT_FLOAT, "cmpl-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CMPL_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CMPL, Type.INT, StdTypeList.DOUBLE_DOUBLE,
                "cmpl-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CMPG_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CMPG, Type.INT, StdTypeList.FLOAT_FLOAT, "cmpg-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CMPG_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CMPG, Type.INT, StdTypeList.DOUBLE_DOUBLE,
                "cmpg-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_L2I =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.INT, StdTypeList.LONG, "conv-l2i");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_F2I =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.INT, StdTypeList.FLOAT, "conv-f2i");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_D2I =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.INT, StdTypeList.DOUBLE, "conv-d2i");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_I2L =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.LONG, StdTypeList.INT, "conv-i2l");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_F2L =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.LONG, StdTypeList.FLOAT, "conv-f2l");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_D2L =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.LONG, StdTypeList.DOUBLE, "conv-d2l");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_I2F =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.FLOAT, StdTypeList.INT, "conv-i2f");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_L2F =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.FLOAT, StdTypeList.LONG, "conv-l2f");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_D2F =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.FLOAT, StdTypeList.DOUBLE, "conv-d2f");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_I2D =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.DOUBLE, StdTypeList.INT, "conv-i2d");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_L2D =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.DOUBLE, StdTypeList.LONG, "conv-l2d");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CONV_F2D =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CONV, Type.DOUBLE, StdTypeList.FLOAT, "conv-f2d");

    public static final mod.agus.jcoderz.dx.rop.code.Rop TO_BYTE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.TO_BYTE, Type.INT, StdTypeList.INT, "to-byte");

    public static final mod.agus.jcoderz.dx.rop.code.Rop TO_CHAR =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.TO_CHAR, Type.INT, StdTypeList.INT, "to-char");

    public static final mod.agus.jcoderz.dx.rop.code.Rop TO_SHORT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.TO_SHORT, Type.INT, StdTypeList.INT, "to-short");

    public static final mod.agus.jcoderz.dx.rop.code.Rop RETURN_VOID =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.RETURN, Type.VOID, StdTypeList.EMPTY, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_RETURN,
                "return-void");

    public static final mod.agus.jcoderz.dx.rop.code.Rop RETURN_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.RETURN, Type.VOID, StdTypeList.INT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_RETURN,
                "return-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop RETURN_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.RETURN, Type.VOID, StdTypeList.LONG, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_RETURN,
                "return-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop RETURN_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.RETURN, Type.VOID, StdTypeList.FLOAT, mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_RETURN,
                "return-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop RETURN_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.RETURN, Type.VOID, StdTypeList.DOUBLE,
                mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_RETURN, "return-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop RETURN_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.RETURN, Type.VOID, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Rop.BRANCH_RETURN, "return-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop ARRAY_LENGTH =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.ARRAY_LENGTH, Type.INT, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException, "array-length");

    public static final mod.agus.jcoderz.dx.rop.code.Rop THROW =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.THROW, Type.VOID, StdTypeList.THROWABLE,
                StdTypeList.THROWABLE, "throw");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MONITOR_ENTER =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MONITOR_ENTER, Type.VOID, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException, "monitor-enter");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MONITOR_EXIT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MONITOR_EXIT, Type.VOID, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_IllegalMonitorStateException,
                "monitor-exit");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AGET_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AGET, Type.INT, StdTypeList.INTARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds,
                "aget-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AGET_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AGET, Type.LONG, StdTypeList.LONGARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds,
                "aget-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AGET_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AGET, Type.FLOAT, StdTypeList.FLOATARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds,
                "aget-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AGET_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AGET, Type.DOUBLE, StdTypeList.DOUBLEARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds,
                "aget-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AGET_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AGET, Type.OBJECT, StdTypeList.OBJECTARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds,
                "aget-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AGET_BOOLEAN =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AGET, Type.INT, StdTypeList.BOOLEANARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds,
                "aget-boolean");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AGET_BYTE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AGET, Type.INT, StdTypeList.BYTEARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds, "aget-byte");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AGET_CHAR =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AGET, Type.INT, StdTypeList.CHARARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds, "aget-char");

    public static final mod.agus.jcoderz.dx.rop.code.Rop AGET_SHORT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.AGET, Type.INT, StdTypeList.SHORTARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds,
                "aget-short");

    public static final mod.agus.jcoderz.dx.rop.code.Rop APUT_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.APUT, Type.VOID, StdTypeList.INT_INTARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds, "aput-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop APUT_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.APUT, Type.VOID, StdTypeList.LONG_LONGARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds, "aput-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop APUT_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.APUT, Type.VOID, StdTypeList.FLOAT_FLOATARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds,
                "aput-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop APUT_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.APUT, Type.VOID, StdTypeList.DOUBLE_DOUBLEARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndexOutOfBounds,
                "aput-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop APUT_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.APUT, Type.VOID, StdTypeList.OBJECT_OBJECTARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndex_ArrayStore,
                "aput-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop APUT_BOOLEAN =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.APUT, Type.VOID, StdTypeList.INT_BOOLEANARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndex_ArrayStore,
                "aput-boolean");

    public static final mod.agus.jcoderz.dx.rop.code.Rop APUT_BYTE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.APUT, Type.VOID, StdTypeList.INT_BYTEARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndex_ArrayStore, "aput-byte");

    public static final mod.agus.jcoderz.dx.rop.code.Rop APUT_CHAR =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.APUT, Type.VOID, StdTypeList.INT_CHARARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndex_ArrayStore, "aput-char");

    public static final mod.agus.jcoderz.dx.rop.code.Rop APUT_SHORT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.APUT, Type.VOID, StdTypeList.INT_SHORTARR_INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_Null_ArrayIndex_ArrayStore,
                "aput-short");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEW_INSTANCE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_INSTANCE, Type.OBJECT, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "new-instance");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEW_ARRAY_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_ARRAY, Type.INT_ARRAY, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NegativeArraySizeException,
                "new-array-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEW_ARRAY_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_ARRAY, Type.LONG_ARRAY, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NegativeArraySizeException,
                "new-array-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEW_ARRAY_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_ARRAY, Type.FLOAT_ARRAY, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NegativeArraySizeException,
                "new-array-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEW_ARRAY_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_ARRAY, Type.DOUBLE_ARRAY, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NegativeArraySizeException,
                "new-array-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEW_ARRAY_BOOLEAN =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_ARRAY, Type.BOOLEAN_ARRAY, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NegativeArraySizeException,
                "new-array-boolean");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEW_ARRAY_BYTE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_ARRAY, Type.BYTE_ARRAY, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NegativeArraySizeException,
                "new-array-byte");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEW_ARRAY_CHAR =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_ARRAY, Type.CHAR_ARRAY, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NegativeArraySizeException,
                "new-array-char");

    public static final mod.agus.jcoderz.dx.rop.code.Rop NEW_ARRAY_SHORT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_ARRAY, Type.SHORT_ARRAY, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NegativeArraySizeException,
                "new-array-short");

    public static final mod.agus.jcoderz.dx.rop.code.Rop CHECK_CAST =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.CHECK_CAST, Type.VOID, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_ClassCastException, "check-cast");

    public static final mod.agus.jcoderz.dx.rop.code.Rop INSTANCE_OF =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.INSTANCE_OF, Type.INT, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "instance-of");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_FIELD_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_FIELD, Type.INT, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException, "get-field-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_FIELD_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_FIELD, Type.LONG, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException, "get-field-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_FIELD_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_FIELD, Type.FLOAT, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "get-field-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_FIELD_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_FIELD, Type.DOUBLE, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "get-field-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_FIELD_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_FIELD, Type.OBJECT, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "get-field-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_FIELD_BOOLEAN =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_FIELD, Type.INT, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "get-field-boolean");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_FIELD_BYTE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_FIELD, Type.INT, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "get-field-byte");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_FIELD_CHAR =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_FIELD, Type.INT, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "get-field-char");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_FIELD_SHORT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_FIELD, Type.INT, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "get-field-short");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_STATIC_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_STATIC, Type.INT, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "get-static-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_STATIC_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_STATIC, Type.LONG, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "get-static-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_STATIC_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_STATIC, Type.FLOAT, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "get-static-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_STATIC_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_STATIC, Type.DOUBLE, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "get-static-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_STATIC_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_STATIC, Type.OBJECT, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "get-static-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_STATIC_BOOLEAN =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_STATIC, Type.INT, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "get-field-boolean");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_STATIC_BYTE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_STATIC, Type.INT, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "get-field-byte");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_STATIC_CHAR =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_STATIC, Type.INT, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "get-field-char");

    public static final mod.agus.jcoderz.dx.rop.code.Rop GET_STATIC_SHORT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.GET_STATIC, Type.INT, StdTypeList.EMPTY,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "get-field-short");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_FIELD_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_FIELD, Type.VOID, StdTypeList.INT_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException, "put-field-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_FIELD_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_FIELD, Type.VOID, StdTypeList.LONG_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException, "put-field-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_FIELD_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_FIELD, Type.VOID, StdTypeList.FLOAT_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "put-field-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_FIELD_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_FIELD, Type.VOID, StdTypeList.DOUBLE_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "put-field-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_FIELD_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_FIELD, Type.VOID, StdTypeList.OBJECT_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "put-field-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_FIELD_BOOLEAN =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_FIELD, Type.VOID, StdTypeList.INT_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "put-field-boolean");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_FIELD_BYTE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_FIELD, Type.VOID, StdTypeList.INT_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "put-field-byte");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_FIELD_CHAR =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_FIELD, Type.VOID, StdTypeList.INT_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "put-field-char");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_FIELD_SHORT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_FIELD, Type.VOID, StdTypeList.INT_OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NullPointerException,
                "put-field-short");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_STATIC_INT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_STATIC, Type.VOID, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "put-static-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_STATIC_LONG =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_STATIC, Type.VOID, StdTypeList.LONG,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "put-static-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_STATIC_FLOAT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_STATIC, Type.VOID, StdTypeList.FLOAT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "put-static-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_STATIC_DOUBLE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_STATIC, Type.VOID, StdTypeList.DOUBLE,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "put-static-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_STATIC_OBJECT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_STATIC, Type.VOID, StdTypeList.OBJECT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "put-static-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_STATIC_BOOLEAN =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_STATIC, Type.VOID, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "put-static-boolean");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_STATIC_BYTE =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_STATIC, Type.VOID, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "put-static-byte");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_STATIC_CHAR =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_STATIC, Type.VOID, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "put-static-char");

    public static final mod.agus.jcoderz.dx.rop.code.Rop PUT_STATIC_SHORT =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.PUT_STATIC, Type.VOID, StdTypeList.INT,
                mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error, "put-static-short");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MARK_LOCAL_INT =
            new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MARK_LOCAL, Type.VOID,
                    StdTypeList.INT, "mark-local-int");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MARK_LOCAL_LONG =
            new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MARK_LOCAL, Type.VOID,
                    StdTypeList.LONG, "mark-local-long");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MARK_LOCAL_FLOAT =
            new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MARK_LOCAL, Type.VOID,
                    StdTypeList.FLOAT, "mark-local-float");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MARK_LOCAL_DOUBLE =
            new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MARK_LOCAL, Type.VOID,
                    StdTypeList.DOUBLE, "mark-local-double");

    public static final mod.agus.jcoderz.dx.rop.code.Rop MARK_LOCAL_OBJECT =
            new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MARK_LOCAL, Type.VOID,
                    StdTypeList.OBJECT, "mark-local-object");

    public static final mod.agus.jcoderz.dx.rop.code.Rop FILL_ARRAY_DATA =
        new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.FILL_ARRAY_DATA, Type.VOID, StdTypeList.EMPTY,
                "fill-array-data");

    public static mod.agus.jcoderz.dx.rop.code.Rop ropFor(int opcode, TypeBearer dest, TypeList sources,
                                                          Constant cst) {
        switch (opcode) {
            case RegOps.NOP: return NOP;
            case RegOps.MOVE: return opMove(dest);
            case RegOps.MOVE_PARAM: return opMoveParam(dest);
            case RegOps.MOVE_EXCEPTION: return opMoveException(dest);
            case RegOps.CONST: return opConst(dest);
            case RegOps.GOTO: return GOTO;
            case RegOps.IF_EQ: return opIfEq(sources);
            case RegOps.IF_NE: return opIfNe(sources);
            case RegOps.IF_LT: return opIfLt(sources);
            case RegOps.IF_GE: return opIfGe(sources);
            case RegOps.IF_LE: return opIfLe(sources);
            case RegOps.IF_GT: return opIfGt(sources);
            case RegOps.SWITCH: return SWITCH;
            case RegOps.ADD: return opAdd(sources);
            case RegOps.SUB: return opSub(sources);
            case RegOps.MUL: return opMul(sources);
            case RegOps.DIV: return opDiv(sources);
            case RegOps.REM: return opRem(sources);
            case RegOps.NEG: return opNeg(dest);
            case RegOps.AND: return opAnd(sources);
            case RegOps.OR: return opOr(sources);
            case RegOps.XOR: return opXor(sources);
            case RegOps.SHL: return opShl(sources);
            case RegOps.SHR: return opShr(sources);
            case RegOps.USHR: return opUshr(sources);
            case RegOps.NOT: return opNot(dest);
            case RegOps.CMPL: return opCmpl(sources.getType(0));
            case RegOps.CMPG: return opCmpg(sources.getType(0));
            case RegOps.CONV: return opConv(dest, sources.getType(0));
            case RegOps.TO_BYTE: return TO_BYTE;
            case RegOps.TO_CHAR: return TO_CHAR;
            case RegOps.TO_SHORT: return TO_SHORT;
            case RegOps.RETURN: {
                if (sources.size() == 0) {
                    return RETURN_VOID;
                }
                return opReturn(sources.getType(0));
            }
            case RegOps.ARRAY_LENGTH: return ARRAY_LENGTH;
            case RegOps.THROW: return THROW;
            case RegOps.MONITOR_ENTER: return MONITOR_ENTER;
            case RegOps.MONITOR_EXIT: return MONITOR_EXIT;
            case RegOps.AGET: {
                Type source = sources.getType(0);
                Type componentType;
                if (source == Type.KNOWN_NULL) {
                    componentType = dest.getType();
                } else {
                    componentType = source.getComponentType();
                }
                return opAget(componentType);
            }
            case RegOps.APUT: {
                Type source = sources.getType(1);
                Type componentType;
                if (source == Type.KNOWN_NULL) {
                    componentType = sources.getType(0);
                } else {
                    componentType = source.getComponentType();
                }
                return opAput(componentType);
            }
            case RegOps.NEW_INSTANCE: return NEW_INSTANCE;
            case RegOps.NEW_ARRAY: return opNewArray(dest.getType());
            case RegOps.CHECK_CAST: return CHECK_CAST;
            case RegOps.INSTANCE_OF: return INSTANCE_OF;
            case RegOps.GET_FIELD: return opGetField(dest);
            case RegOps.GET_STATIC: return opGetStatic(dest);
            case RegOps.PUT_FIELD: return opPutField(sources.getType(0));
            case RegOps.PUT_STATIC: return opPutStatic(sources.getType(0));
            case RegOps.INVOKE_STATIC: {
                return opInvokeStatic(((mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst).getPrototype());
            }
            case RegOps.INVOKE_VIRTUAL: {
                mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef cstMeth = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst;
                Prototype meth = cstMeth.getPrototype();
                mod.agus.jcoderz.dx.rop.cst.CstType definer = cstMeth.getDefiningClass();
                meth = meth.withFirstParameter(definer.getClassType());
                return opInvokeVirtual(meth);
            }
            case RegOps.INVOKE_SUPER: {
                mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef cstMeth = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst;
                Prototype meth = cstMeth.getPrototype();
                mod.agus.jcoderz.dx.rop.cst.CstType definer = cstMeth.getDefiningClass();
                meth = meth.withFirstParameter(definer.getClassType());
                return opInvokeSuper(meth);
            }
            case RegOps.INVOKE_DIRECT: {
                mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef cstMeth = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst;
                Prototype meth = cstMeth.getPrototype();
                mod.agus.jcoderz.dx.rop.cst.CstType definer = cstMeth.getDefiningClass();
                meth = meth.withFirstParameter(definer.getClassType());
                return opInvokeDirect(meth);
            }
            case RegOps.INVOKE_INTERFACE: {
                mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef cstMeth = (mod.agus.jcoderz.dx.rop.cst.CstMethodRef) cst;
                Prototype meth = cstMeth.getPrototype();
                mod.agus.jcoderz.dx.rop.cst.CstType definer = cstMeth.getDefiningClass();
                meth = meth.withFirstParameter(definer.getClassType());
                return opInvokeInterface(meth);
            }
            case RegOps.INVOKE_POLYMORPHIC: {
                CstBaseMethodRef cstMeth = (CstMethodRef) cst;
                Prototype proto = cstMeth.getPrototype();
                CstType definer = cstMeth.getDefiningClass();
                Prototype meth = proto.withFirstParameter(definer.getClassType());
                return opInvokePolymorphic(meth);
            }
            case RegOps.INVOKE_CUSTOM: {
                mod.agus.jcoderz.dx.rop.cst.CstCallSiteRef cstInvokeDynamicRef = (CstCallSiteRef) cst;
                Prototype proto = cstInvokeDynamicRef.getPrototype();
                return opInvokeCustom(proto);
            }
        }

        throw new RuntimeException("unknown opcode " + RegOps.opName(opcode));
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opMove(TypeBearer type) {
        switch (type.getBasicFrameType()) {
            case Type.BT_INT:    return MOVE_INT;
            case Type.BT_LONG:   return MOVE_LONG;
            case Type.BT_FLOAT:  return MOVE_FLOAT;
            case Type.BT_DOUBLE: return MOVE_DOUBLE;
            case Type.BT_OBJECT: return MOVE_OBJECT;
            case Type.BT_ADDR:   return MOVE_RETURN_ADDRESS;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opMoveParam(TypeBearer type) {
        switch (type.getBasicFrameType()) {
            case Type.BT_INT:    return MOVE_PARAM_INT;
            case Type.BT_LONG:   return MOVE_PARAM_LONG;
            case Type.BT_FLOAT:  return MOVE_PARAM_FLOAT;
            case Type.BT_DOUBLE: return MOVE_PARAM_DOUBLE;
            case Type.BT_OBJECT: return MOVE_PARAM_OBJECT;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opMoveException(TypeBearer type) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE_EXCEPTION, type.getType(),
                       StdTypeList.EMPTY, (String) null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opMoveResult(TypeBearer type) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE_RESULT, type.getType(),
                       StdTypeList.EMPTY, (String) null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opMoveResultPseudo(TypeBearer type) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.MOVE_RESULT_PSEUDO, type.getType(),
                       StdTypeList.EMPTY, (String) null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opConst(TypeBearer type) {
        if (type.getType() == Type.KNOWN_NULL) {
            return CONST_OBJECT_NOTHROW;
        }

        switch (type.getBasicFrameType()) {
            case Type.BT_INT:    return CONST_INT;
            case Type.BT_LONG:   return CONST_LONG;
            case Type.BT_FLOAT:  return CONST_FLOAT;
            case Type.BT_DOUBLE: return CONST_DOUBLE;
            case Type.BT_OBJECT: return CONST_OBJECT;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opIfEq(TypeList types) {
        return pickIf(types, IF_EQZ_INT, IF_EQZ_OBJECT,
                      IF_EQ_INT, IF_EQ_OBJECT);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opIfNe(TypeList types) {
        return pickIf(types, IF_NEZ_INT, IF_NEZ_OBJECT,
                      IF_NE_INT, IF_NE_OBJECT);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opIfLt(TypeList types) {
        return pickIf(types, IF_LTZ_INT, null, IF_LT_INT, null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opIfGe(TypeList types) {
        return pickIf(types, IF_GEZ_INT, null, IF_GE_INT, null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opIfGt(TypeList types) {
        return pickIf(types, IF_GTZ_INT, null, IF_GT_INT, null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opIfLe(TypeList types) {
        return pickIf(types, IF_LEZ_INT, null, IF_LE_INT, null);
    }

    private static mod.agus.jcoderz.dx.rop.code.Rop pickIf(TypeList types, mod.agus.jcoderz.dx.rop.code.Rop intZ, mod.agus.jcoderz.dx.rop.code.Rop objZ, mod.agus.jcoderz.dx.rop.code.Rop intInt,
                                                           mod.agus.jcoderz.dx.rop.code.Rop objObj) {
        switch(types.size()) {
            case 1: {
                switch (types.getType(0).getBasicFrameType()) {
                    case Type.BT_INT: {
                        return intZ;
                    }
                    case Type.BT_OBJECT: {
                        if (objZ != null) {
                            return objZ;
                        }
                    }
                }
                break;
            }
            case 2: {
                int bt = types.getType(0).getBasicFrameType();
                if (bt == types.getType(1).getBasicFrameType()) {
                    switch (bt) {
                        case Type.BT_INT: {
                            return intInt;
                        }
                        case Type.BT_OBJECT: {
                            if (objObj != null) {
                                return objObj;
                            }
                        }
                    }
                }
                break;
            }
        }

        return throwBadTypes(types);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opAdd(TypeList types) {
        return pickBinaryOp(types, ADD_CONST_INT, ADD_CONST_LONG,
                            ADD_CONST_FLOAT, ADD_CONST_DOUBLE, ADD_INT,
                            ADD_LONG, ADD_FLOAT, ADD_DOUBLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opSub(TypeList types) {
        return pickBinaryOp(types, SUB_CONST_INT, SUB_CONST_LONG,
                            SUB_CONST_FLOAT, SUB_CONST_DOUBLE, SUB_INT,
                            SUB_LONG, SUB_FLOAT, SUB_DOUBLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opMul(TypeList types) {
        return pickBinaryOp(types, MUL_CONST_INT, MUL_CONST_LONG,
                            MUL_CONST_FLOAT, MUL_CONST_DOUBLE, MUL_INT,
                            MUL_LONG, MUL_FLOAT, MUL_DOUBLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opDiv(TypeList types) {
        return pickBinaryOp(types, DIV_CONST_INT, DIV_CONST_LONG,
                            DIV_CONST_FLOAT, DIV_CONST_DOUBLE, DIV_INT,
                            DIV_LONG, DIV_FLOAT, DIV_DOUBLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opRem(TypeList types) {
        return pickBinaryOp(types, REM_CONST_INT, REM_CONST_LONG,
                            REM_CONST_FLOAT, REM_CONST_DOUBLE, REM_INT,
                            REM_LONG, REM_FLOAT, REM_DOUBLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opAnd(TypeList types) {
        return pickBinaryOp(types, AND_CONST_INT, AND_CONST_LONG, null, null,
                            AND_INT, AND_LONG, null, null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opOr(TypeList types) {
        return pickBinaryOp(types, OR_CONST_INT, OR_CONST_LONG, null, null,
                            OR_INT, OR_LONG, null, null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opXor(TypeList types) {
        return pickBinaryOp(types, XOR_CONST_INT, XOR_CONST_LONG, null, null,
                            XOR_INT, XOR_LONG, null, null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opShl(TypeList types) {
        return pickBinaryOp(types, SHL_CONST_INT, SHL_CONST_LONG, null, null,
                            SHL_INT, SHL_LONG, null, null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opShr(TypeList types) {
        return pickBinaryOp(types, SHR_CONST_INT, SHR_CONST_LONG, null, null,
                            SHR_INT, SHR_LONG, null, null);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opUshr(TypeList types) {
        return pickBinaryOp(types, USHR_CONST_INT, USHR_CONST_LONG, null, null,
                            USHR_INT, USHR_LONG, null, null);
    }

    private static mod.agus.jcoderz.dx.rop.code.Rop pickBinaryOp(TypeList types, mod.agus.jcoderz.dx.rop.code.Rop int1, mod.agus.jcoderz.dx.rop.code.Rop long1,
                                                                 mod.agus.jcoderz.dx.rop.code.Rop float1, mod.agus.jcoderz.dx.rop.code.Rop double1, mod.agus.jcoderz.dx.rop.code.Rop int2,
                                                                 mod.agus.jcoderz.dx.rop.code.Rop long2, mod.agus.jcoderz.dx.rop.code.Rop float2, mod.agus.jcoderz.dx.rop.code.Rop double2) {
        int bt1 = types.getType(0).getBasicFrameType();
        mod.agus.jcoderz.dx.rop.code.Rop result = null;

        switch (types.size()) {
            case 1: {
                switch(bt1) {
                    case Type.BT_INT:    return int1;
                    case Type.BT_LONG:   return long1;
                    case Type.BT_FLOAT:  result = float1; break;
                    case Type.BT_DOUBLE: result = double1; break;
                }
                break;
            }
            case 2: {
                switch(bt1) {
                    case Type.BT_INT:    return int2;
                    case Type.BT_LONG:   return long2;
                    case Type.BT_FLOAT:  result = float2; break;
                    case Type.BT_DOUBLE: result = double2; break;
                }
                break;
            }
        }

        if (result == null) {
            return throwBadTypes(types);
        }

        return result;
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opNeg(TypeBearer type) {
        switch (type.getBasicFrameType()) {
            case Type.BT_INT:    return NEG_INT;
            case Type.BT_LONG:   return NEG_LONG;
            case Type.BT_FLOAT:  return NEG_FLOAT;
            case Type.BT_DOUBLE: return NEG_DOUBLE;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opNot(TypeBearer type) {
        switch (type.getBasicFrameType()) {
            case Type.BT_INT:  return NOT_INT;
            case Type.BT_LONG: return NOT_LONG;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opCmpl(TypeBearer type) {
        switch (type.getBasicType()) {
            case Type.BT_LONG:   return CMPL_LONG;
            case Type.BT_FLOAT:  return CMPL_FLOAT;
            case Type.BT_DOUBLE: return CMPL_DOUBLE;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opCmpg(TypeBearer type) {
        switch (type.getBasicType()) {
            case Type.BT_FLOAT:  return CMPG_FLOAT;
            case Type.BT_DOUBLE: return CMPG_DOUBLE;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opConv(TypeBearer dest, TypeBearer source) {
        int dbt = dest.getBasicFrameType();
        switch (source.getBasicFrameType()) {
            case Type.BT_INT: {
                switch (dbt) {
                    case Type.BT_LONG:   return CONV_I2L;
                    case Type.BT_FLOAT:  return CONV_I2F;
                    case Type.BT_DOUBLE: return CONV_I2D;
                    default:             break;
                }
            }
            case Type.BT_LONG: {
                switch (dbt) {
                    case Type.BT_INT:    return CONV_L2I;
                    case Type.BT_FLOAT:  return CONV_L2F;
                    case Type.BT_DOUBLE: return CONV_L2D;
                    default:             break;
                }
            }
            case Type.BT_FLOAT: {
                switch (dbt) {
                    case Type.BT_INT:    return CONV_F2I;
                    case Type.BT_LONG:   return CONV_F2L;
                    case Type.BT_DOUBLE: return CONV_F2D;
                    default:             break;
                }
            }
            case Type.BT_DOUBLE: {
                switch (dbt) {
                    case Type.BT_INT:    return CONV_D2I;
                    case Type.BT_LONG:   return CONV_D2L;
                    case Type.BT_FLOAT:  return CONV_D2F;
                    default:             break;
                }
            }
        }

        return throwBadTypes(StdTypeList.make(dest.getType(),
                                              source.getType()));
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opReturn(TypeBearer type) {
        switch (type.getBasicFrameType()) {
            case Type.BT_INT:    return RETURN_INT;
            case Type.BT_LONG:   return RETURN_LONG;
            case Type.BT_FLOAT:  return RETURN_FLOAT;
            case Type.BT_DOUBLE: return RETURN_DOUBLE;
            case Type.BT_OBJECT: return RETURN_OBJECT;
            case Type.BT_VOID:   return RETURN_VOID;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opAget(TypeBearer type) {
        switch (type.getBasicType()) {
            case Type.BT_INT:     return AGET_INT;
            case Type.BT_LONG:    return AGET_LONG;
            case Type.BT_FLOAT:   return AGET_FLOAT;
            case Type.BT_DOUBLE:  return AGET_DOUBLE;
            case Type.BT_OBJECT:  return AGET_OBJECT;
            case Type.BT_BOOLEAN: return AGET_BOOLEAN;
            case Type.BT_BYTE:    return AGET_BYTE;
            case Type.BT_CHAR:    return AGET_CHAR;
            case Type.BT_SHORT:   return AGET_SHORT;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opAput(TypeBearer type) {
        switch (type.getBasicType()) {
            case Type.BT_INT:     return APUT_INT;
            case Type.BT_LONG:    return APUT_LONG;
            case Type.BT_FLOAT:   return APUT_FLOAT;
            case Type.BT_DOUBLE:  return APUT_DOUBLE;
            case Type.BT_OBJECT:  return APUT_OBJECT;
            case Type.BT_BOOLEAN: return APUT_BOOLEAN;
            case Type.BT_BYTE:    return APUT_BYTE;
            case Type.BT_CHAR:    return APUT_CHAR;
            case Type.BT_SHORT:   return APUT_SHORT;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opNewArray(TypeBearer arrayType) {
        Type type = arrayType.getType();
        Type elementType = type.getComponentType();

        switch (elementType.getBasicType()) {
            case Type.BT_INT:     return NEW_ARRAY_INT;
            case Type.BT_LONG:    return NEW_ARRAY_LONG;
            case Type.BT_FLOAT:   return NEW_ARRAY_FLOAT;
            case Type.BT_DOUBLE:  return NEW_ARRAY_DOUBLE;
            case Type.BT_BOOLEAN: return NEW_ARRAY_BOOLEAN;
            case Type.BT_BYTE:    return NEW_ARRAY_BYTE;
            case Type.BT_CHAR:    return NEW_ARRAY_CHAR;
            case Type.BT_SHORT:   return NEW_ARRAY_SHORT;
            case Type.BT_OBJECT: {
                return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.NEW_ARRAY, type, StdTypeList.INT,
                        mod.agus.jcoderz.dx.rop.code.Exceptions.LIST_Error_NegativeArraySizeException,
                        "new-array-object");
            }
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opFilledNewArray(TypeBearer arrayType, int count) {
        Type type = arrayType.getType();
        Type elementType = type.getComponentType();

        if (elementType.isCategory2()) {
            return throwBadType(arrayType);
        }

        if (count < 0) {
            throw new IllegalArgumentException("count < 0");
        }

        StdTypeList sourceTypes = new StdTypeList(count);

        for (int i = 0; i < count; i++) {
            sourceTypes.set(i, elementType);
        }


        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.FILLED_NEW_ARRAY,
                       sourceTypes,
                       Exceptions.LIST_Error);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opGetField(TypeBearer type) {
        switch (type.getBasicType()) {
            case Type.BT_INT:     return GET_FIELD_INT;
            case Type.BT_LONG:    return GET_FIELD_LONG;
            case Type.BT_FLOAT:   return GET_FIELD_FLOAT;
            case Type.BT_DOUBLE:  return GET_FIELD_DOUBLE;
            case Type.BT_OBJECT:  return GET_FIELD_OBJECT;
            case Type.BT_BOOLEAN: return GET_FIELD_BOOLEAN;
            case Type.BT_BYTE:    return GET_FIELD_BYTE;
            case Type.BT_CHAR:    return GET_FIELD_CHAR;
            case Type.BT_SHORT:   return GET_FIELD_SHORT;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opPutField(TypeBearer type) {
        switch (type.getBasicType()) {
            case Type.BT_INT:     return PUT_FIELD_INT;
            case Type.BT_LONG:    return PUT_FIELD_LONG;
            case Type.BT_FLOAT:   return PUT_FIELD_FLOAT;
            case Type.BT_DOUBLE:  return PUT_FIELD_DOUBLE;
            case Type.BT_OBJECT:  return PUT_FIELD_OBJECT;
            case Type.BT_BOOLEAN: return PUT_FIELD_BOOLEAN;
            case Type.BT_BYTE:    return PUT_FIELD_BYTE;
            case Type.BT_CHAR:    return PUT_FIELD_CHAR;
            case Type.BT_SHORT:   return PUT_FIELD_SHORT;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opGetStatic(TypeBearer type) {
        switch (type.getBasicType()) {
            case Type.BT_INT:     return GET_STATIC_INT;
            case Type.BT_LONG:    return GET_STATIC_LONG;
            case Type.BT_FLOAT:   return GET_STATIC_FLOAT;
            case Type.BT_DOUBLE:  return GET_STATIC_DOUBLE;
            case Type.BT_OBJECT:  return GET_STATIC_OBJECT;
            case Type.BT_BOOLEAN: return GET_STATIC_BOOLEAN;
            case Type.BT_BYTE:    return GET_STATIC_BYTE;
            case Type.BT_CHAR:    return GET_STATIC_CHAR;
            case Type.BT_SHORT:   return GET_STATIC_SHORT;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opPutStatic(TypeBearer type) {
        switch (type.getBasicType()) {
            case Type.BT_INT:     return PUT_STATIC_INT;
            case Type.BT_LONG:    return PUT_STATIC_LONG;
            case Type.BT_FLOAT:   return PUT_STATIC_FLOAT;
            case Type.BT_DOUBLE:  return PUT_STATIC_DOUBLE;
            case Type.BT_OBJECT:  return PUT_STATIC_OBJECT;
            case Type.BT_BOOLEAN: return PUT_STATIC_BOOLEAN;
            case Type.BT_BYTE:    return PUT_STATIC_BYTE;
            case Type.BT_CHAR:    return PUT_STATIC_CHAR;
            case Type.BT_SHORT:   return PUT_STATIC_SHORT;
        }

        return throwBadType(type);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opInvokeStatic(Prototype meth) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.INVOKE_STATIC,
                       meth.getParameterFrameTypes(),
                       StdTypeList.THROWABLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opInvokeVirtual(Prototype meth) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.INVOKE_VIRTUAL,
                       meth.getParameterFrameTypes(),
                       StdTypeList.THROWABLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opInvokeSuper(Prototype meth) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.INVOKE_SUPER,
                       meth.getParameterFrameTypes(),
                       StdTypeList.THROWABLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opInvokeDirect(Prototype meth) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.INVOKE_DIRECT,
                       meth.getParameterFrameTypes(),
                       StdTypeList.THROWABLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opInvokeInterface(Prototype meth) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.INVOKE_INTERFACE,
                       meth.getParameterFrameTypes(),
                       StdTypeList.THROWABLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opInvokePolymorphic(Prototype meth) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.INVOKE_POLYMORPHIC,
                       meth.getParameterFrameTypes(),
                       StdTypeList.THROWABLE);
    }

    private static mod.agus.jcoderz.dx.rop.code.Rop opInvokeCustom(Prototype meth) {
        return new mod.agus.jcoderz.dx.rop.code.Rop(RegOps.INVOKE_CUSTOM,
                       meth.getParameterFrameTypes(),
                       StdTypeList.THROWABLE);
    }

    public static mod.agus.jcoderz.dx.rop.code.Rop opMarkLocal(TypeBearer type) {
        switch (type.getBasicFrameType()) {
            case Type.BT_INT:    return MARK_LOCAL_INT;
            case Type.BT_LONG:   return MARK_LOCAL_LONG;
            case Type.BT_FLOAT:  return MARK_LOCAL_FLOAT;
            case Type.BT_DOUBLE: return MARK_LOCAL_DOUBLE;
            case Type.BT_OBJECT: return MARK_LOCAL_OBJECT;
        }

        return throwBadType(type);
    }

    private Rops() {

    }

    private static mod.agus.jcoderz.dx.rop.code.Rop throwBadType(TypeBearer type) {
        throw new IllegalArgumentException("bad type: " + type);
    }

    private static Rop throwBadTypes(TypeList types) {
        throw new IllegalArgumentException("bad types: " + types);
    }
}
