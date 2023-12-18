package com.peecko.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ArticleItem.
 */
@Entity
@Table(name = "article_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "previous")
    private String previous;

    @Column(name = "code")
    private String code;

    @Column(name = "next")
    private String next;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "articleItems", "apsUser" }, allowSetters = true)
    private ArticleList articleList;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArticleItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrevious() {
        return this.previous;
    }

    public ArticleItem previous(String previous) {
        this.setPrevious(previous);
        return this;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getCode() {
        return this.code;
    }

    public ArticleItem code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNext() {
        return this.next;
    }

    public ArticleItem next(String next) {
        this.setNext(next);
        return this;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public ArticleList getArticleList() {
        return this.articleList;
    }

    public void setArticleList(ArticleList articleList) {
        this.articleList = articleList;
    }

    public ArticleItem articleList(ArticleList articleList) {
        this.setArticleList(articleList);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleItem)) {
            return false;
        }
        return getId() != null && getId().equals(((ArticleItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleItem{" +
            "id=" + getId() +
            ", previous='" + getPrevious() + "'" +
            ", code='" + getCode() + "'" +
            ", next='" + getNext() + "'" +
            "}";
    }
}
