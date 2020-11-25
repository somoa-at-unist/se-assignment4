package janala.instrument;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class SnoopInstructionMethodAdapterForFL extends SnoopInstructionMethodAdapter {

    public SnoopInstructionMethodAdapterForFL(MethodVisitor mv, String className,
                                              String methodName, String descriptor, String superName,
                                              GlobalStateForInstrumentation instrumentationState) {
        super(mv, className, methodName, descriptor, superName, instrumentationState);
    }

    @Override
    public void visitLineNumber(int lineNumber, Label label) {
        lastLineNumber = lineNumber;
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        addInvokeLogMethod(mv);
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLdcInsn(Object cst) {
        addInvokeLogMethod(mv);
        super.visitLdcInsn(cst);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        addInvokeLogMethod(mv);
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        addInvokeLogMethod(mv);
        super.visitVarInsn(opcode, var);

    }

    @Override
    public void visitInsn(int opcode) {
        switch (opcode) {
            case ACONST_NULL:
            case ICONST_M1:
            case ICONST_0:
            case ICONST_1:
            case ICONST_2:
            case ICONST_3:
            case ICONST_4:
            case ICONST_5:
            case LCONST_0:
            case LCONST_1:
            case FCONST_0:
            case FCONST_1:
            case FCONST_2:
            case DCONST_0:
            case DCONST_1:
            case IALOAD:
            case LALOAD:
            case FALOAD:
            case DALOAD:
            case AALOAD:
            case BALOAD:
            case CALOAD:
            case SALOAD:
            case IASTORE:
            case LASTORE:
            case FASTORE:
            case DASTORE:
            case AASTORE:
            case BASTORE:
            case CASTORE:
            case SASTORE:
            case IADD:
            case LADD:
            case FADD:
            case DADD:
            case ISUB:
            case LSUB:
            case FSUB:
            case DSUB:
            case IMUL:
            case LMUL:
            case FMUL:
            case DMUL:
            case IDIV:
            case LDIV:
            case FDIV:
            case DDIV:
            case IREM:
            case LREM:
            case FREM:
            case DREM:
            case INEG:
            case LNEG:
            case FNEG:
            case DNEG:
            case ISHL:
            case LSHL:
            case ISHR:
            case LSHR:
            case IUSHR:
            case LUSHR:
            case IAND:
            case LAND:
            case IOR:
            case LOR:
            case IXOR:
            case LXOR:
            case I2L:
            case I2F:
            case I2D:
            case L2I:
            case L2F:
            case L2D:
            case F2I:
            case F2L:
            case F2D:
            case D2I:
            case D2L:
            case D2F:
            case I2B:
            case I2C:
            case I2S:
            case LCMP:
            case FCMPL:
            case FCMPG:
            case DCMPL:
            case DCMPG:
            case IRETURN:
            case LRETURN:
            case FRETURN:
            case DRETURN:
            case ARETURN:
            case RETURN:
            addInvokeLogMethod(mv);
            default:
                mv.visitInsn(opcode); // Don't instrument other instructions
        }
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        addInvokeLogMethod(mv);
        super.visitFieldInsn(opcode, owner, name, desc);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        addInvokeLogMethod(mv);
        super.visitTypeInsn(opcode, type);
    }


    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        // Save operand value
        addInvokeLogMethod(mv);
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        // Save operand value
        addInvokeLogMethod(mv);
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }


}
