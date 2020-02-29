package group.iiicestseb.backend.entity;

/**
 * @author jh
 * @date 2020/2/29
 */
public class TermStandard {
    private Integer id;

    private String name;

    public TermStandard(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TermStandard() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}