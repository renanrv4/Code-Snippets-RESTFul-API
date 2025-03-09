package dio.lab.restapi.domain.model;

import jakarta.persistence.*;
import org.w3c.dom.Text;

import java.util.Date;

@Entity(name = "tb_snippets")
public class Snippet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @Column(length = 100)
    private String title;

    private Text code;

    private String language;

    private Date createdAt;

    private Date updatedAt;

    @MapsId
    private User userId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUserId() { return userId; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public Text getCode() { return code; }
    public void setCode(Text code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
