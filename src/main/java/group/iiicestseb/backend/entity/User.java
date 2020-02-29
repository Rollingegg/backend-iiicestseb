package group.iiicestseb.backend.entity;

public class User {
    private Integer id;

    private Integer recordId;

    private String username;

    private String password;

    private String privilegeLevel;

    public User(Integer id, Integer recordId, String username, String password, String privilegeLevel) {
        this.id = id;
        this.recordId = recordId;
        this.username = username;
        this.password = password;
        this.privilegeLevel = privilegeLevel;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setPrivilegeLevel(String privilegeLevel) {
        this.privilegeLevel = privilegeLevel == null ? null : privilegeLevel.trim();
    }
}