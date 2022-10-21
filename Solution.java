public interface File extends Visitable {
    String name();
    byte[] bytes();
}

public class Folder implements File {

    private String name;
    private byte[] bytes;
    private List<File> children;

    public Folder(String name, byte[] bytes, List<File> children) {
        this.name = name;
        this.bytes = bytes;
        this.children = children;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public byte[] bytes() {
        return this.bytes;
    }

    public List<File> children() {
        return this.children;
    }
}

public class SingleFile implements File {

    private String name;
    private byte[] bytes;

    public SingleFile(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public byte[] bytes() {
        return this.bytes;
    }
}

public class SizeVisitor implements Visitor<Integer> {
    @Override
    public Integer visit(SingleFile file) {
        return file.bytes().length;
    }

    @Override
    public Integer visit(Folder folder) {
        return folder.children().stream().map(file -> file.accept(this)).reduce(0, (a, b) -> a + b);
    }
}

public interface Visitable {

    <T> T accept(Visitor<T> visitor);
}

public interface Visitor<T> {

    T visit(SingleFile file);
    T visit(Folder file);
}
