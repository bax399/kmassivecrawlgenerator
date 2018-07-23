//Code modified from: https://gist.github.com/smddzcy/bf8fc17dedf4d40b0a873fc44f855a58
package model.graphresource;
public class Vertex {
    private int uniqueLabel;

    public Vertex(int uniqueLabel) {
        super();
        this.uniqueLabel = uniqueLabel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vertex)) return false;

        Vertex _obj = (Vertex) obj;
        return _obj.uniqueLabel == uniqueLabel;
    }

    @Override
    public int hashCode() {
        return uniqueLabel;
    }

    public int getLabel() {
        return uniqueLabel;
    }

    public void setLabel(int uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }
}
