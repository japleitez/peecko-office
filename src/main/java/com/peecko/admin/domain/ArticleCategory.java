package com.peecko.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ArticleCategory.
 */
@Entity
@Table(name = "article_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "created")
    private Instant created;

    @Column(name = "release")
    private Instant release;

    @Column(name = "archived")
    private Instant archived;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "articleCategory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articleCategory", "coach" }, allowSetters = true)
    private Set<Article> articles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArticleCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public ArticleCategory code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public ArticleCategory title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return this.label;
    }

    public ArticleCategory label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Instant getCreated() {
        return this.created;
    }

    public ArticleCategory created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getRelease() {
        return this.release;
    }

    public ArticleCategory release(Instant release) {
        this.setRelease(release);
        return this;
    }

    public void setRelease(Instant release) {
        this.release = release;
    }

    public Instant getArchived() {
        return this.archived;
    }

    public ArticleCategory archived(Instant archived) {
        this.setArchived(archived);
        return this;
    }

    public void setArchived(Instant archived) {
        this.archived = archived;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setArticleCategory(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setArticleCategory(this));
        }
        this.articles = articles;
    }

    public ArticleCategory articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public ArticleCategory addArticle(Article article) {
        this.articles.add(article);
        article.setArticleCategory(this);
        return this;
    }

    public ArticleCategory removeArticle(Article article) {
        this.articles.remove(article);
        article.setArticleCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleCategory)) {
            return false;
        }
        return getId() != null && getId().equals(((ArticleCategory) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleCategory{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", label='" + getLabel() + "'" +
            ", created='" + getCreated() + "'" +
            ", release='" + getRelease() + "'" +
            ", archived='" + getArchived() + "'" +
            "}";
    }
}
