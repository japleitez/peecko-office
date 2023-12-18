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
 * A ArticleList.
 */
@Entity
@Table(name = "article_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "counter", nullable = false)
    private Integer counter;

    @NotNull
    @Column(name = "created", nullable = false)
    private Instant created;

    @NotNull
    @Column(name = "updated", nullable = false)
    private Instant updated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "articleList")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articleList" }, allowSetters = true)
    private Set<ArticleItem> articleItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "apsDevices", "playLists", "articleLists" }, allowSetters = true)
    private ApsUser apsUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArticleList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ArticleList name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCounter() {
        return this.counter;
    }

    public ArticleList counter(Integer counter) {
        this.setCounter(counter);
        return this;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Instant getCreated() {
        return this.created;
    }

    public ArticleList created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public ArticleList updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Set<ArticleItem> getArticleItems() {
        return this.articleItems;
    }

    public void setArticleItems(Set<ArticleItem> articleItems) {
        if (this.articleItems != null) {
            this.articleItems.forEach(i -> i.setArticleList(null));
        }
        if (articleItems != null) {
            articleItems.forEach(i -> i.setArticleList(this));
        }
        this.articleItems = articleItems;
    }

    public ArticleList articleItems(Set<ArticleItem> articleItems) {
        this.setArticleItems(articleItems);
        return this;
    }

    public ArticleList addArticleItem(ArticleItem articleItem) {
        this.articleItems.add(articleItem);
        articleItem.setArticleList(this);
        return this;
    }

    public ArticleList removeArticleItem(ArticleItem articleItem) {
        this.articleItems.remove(articleItem);
        articleItem.setArticleList(null);
        return this;
    }

    public ApsUser getApsUser() {
        return this.apsUser;
    }

    public void setApsUser(ApsUser apsUser) {
        this.apsUser = apsUser;
    }

    public ArticleList apsUser(ApsUser apsUser) {
        this.setApsUser(apsUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleList)) {
            return false;
        }
        return getId() != null && getId().equals(((ArticleList) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", counter=" + getCounter() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
