package group.iiicestseb.backend.entity;

public class Term {
    private Integer id;

    private Integer standardId;

    private String word;

    public Term(Integer id, Integer standardId, String word) {
        this.id = id;
        this.standardId = standardId;
        this.word = word;
    }

    public Term() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }
}