package cite.ansteph.ponda.model;

/**
 * Created by loicstephan on 2018/02/22.
 */

public class LoicTest {

    int id;
    String Name;

    public LoicTest(int id, String name) {
        this.id = id;
        Name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
