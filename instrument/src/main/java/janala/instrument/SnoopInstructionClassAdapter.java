
package janala.instrument;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class SnoopInstructionClassAdapter extends ClassVisitor {
  private final String className;
  private String superName;
  private String fileName;

  public SnoopInstructionClassAdapter(ClassVisitor cv, String className) {

    super(Opcodes.ASM8, cv);
    this.className = className;
  }

  @Override
  public void visit(int version,
                    int access,
                    String name,
                    String signature,
                    String superName,
                    String[] interfaces) {
    assert name.equals(this.className);
    this.superName = superName;
    cv.visit(version, access, name, signature, superName, interfaces);
  }

  @Override
  public void visitSource(final String source, final String debug) {
    super.visitSource(source, debug);
    this.fileName = source;
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, 
      String signature, String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    if (mv != null) {
      return new SnoopInstructionMethodAdapterForFL(mv, className, name, desc, superName,
          GlobalStateForInstrumentation.instance);
    }
    return null;
  }

  public String getFileName() {
    return this.fileName;
  }
}
