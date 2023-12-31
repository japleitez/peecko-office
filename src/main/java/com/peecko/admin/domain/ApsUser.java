package com.peecko.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peecko.admin.domain.enumeration.Language;
import com.peecko.admin.domain.enumeration.YesNo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApsUser.
 */
@Entity
@Table(name = "aps_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApsUser implements Serializable {

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
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "username_verified", nullable = false)
    private YesNo usernameVerified;

    @NotNull
    @Column(name = "private_email", nullable = false)
    private String privateEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "private_verified", nullable = false)
    private YesNo privateVerified;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @Column(name = "license")
    private String license;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "active", nullable = false)
    private YesNo active;

    @Column(name = "password")
    private String password;

    @Column(name = "created")
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apsUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apsUser" }, allowSetters = true)
    private Set<ApsDevice> apsDevices = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apsUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "videoItems", "apsUser" }, allowSetters = true)
    private Set<PlayList> playLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apsUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articleItems", "apsUser" }, allowSetters = true)
    private Set<ArticleList> articleLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApsUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ApsUser name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public ApsUser username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public YesNo getUsernameVerified() {
        return this.usernameVerified;
    }

    public ApsUser usernameVerified(YesNo usernameVerified) {
        this.setUsernameVerified(usernameVerified);
        return this;
    }

    public void setUsernameVerified(YesNo usernameVerified) {
        this.usernameVerified = usernameVerified;
    }

    public String getPrivateEmail() {
        return this.privateEmail;
    }

    public ApsUser privateEmail(String privateEmail) {
        this.setPrivateEmail(privateEmail);
        return this;
    }

    public void setPrivateEmail(String privateEmail) {
        this.privateEmail = privateEmail;
    }

    public YesNo getPrivateVerified() {
        return this.privateVerified;
    }

    public ApsUser privateVerified(YesNo privateVerified) {
        this.setPrivateVerified(privateVerified);
        return this;
    }

    public void setPrivateVerified(YesNo privateVerified) {
        this.privateVerified = privateVerified;
    }

    public Language getLanguage() {
        return this.language;
    }

    public ApsUser language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getLicense() {
        return this.license;
    }

    public ApsUser license(String license) {
        this.setLicense(license);
        return this;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public YesNo getActive() {
        return this.active;
    }

    public ApsUser active(YesNo active) {
        this.setActive(active);
        return this;
    }

    public void setActive(YesNo active) {
        this.active = active;
    }

    public String getPassword() {
        return this.password;
    }

    public ApsUser password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreated() {
        return this.created;
    }

    public ApsUser created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public ApsUser updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Set<ApsDevice> getApsDevices() {
        return this.apsDevices;
    }

    public void setApsDevices(Set<ApsDevice> apsDevices) {
        if (this.apsDevices != null) {
            this.apsDevices.forEach(i -> i.setApsUser(null));
        }
        if (apsDevices != null) {
            apsDevices.forEach(i -> i.setApsUser(this));
        }
        this.apsDevices = apsDevices;
    }

    public ApsUser apsDevices(Set<ApsDevice> apsDevices) {
        this.setApsDevices(apsDevices);
        return this;
    }

    public ApsUser addApsDevice(ApsDevice apsDevice) {
        this.apsDevices.add(apsDevice);
        apsDevice.setApsUser(this);
        return this;
    }

    public ApsUser removeApsDevice(ApsDevice apsDevice) {
        this.apsDevices.remove(apsDevice);
        apsDevice.setApsUser(null);
        return this;
    }

    public Set<PlayList> getPlayLists() {
        return this.playLists;
    }

    public void setPlayLists(Set<PlayList> playLists) {
        if (this.playLists != null) {
            this.playLists.forEach(i -> i.setApsUser(null));
        }
        if (playLists != null) {
            playLists.forEach(i -> i.setApsUser(this));
        }
        this.playLists = playLists;
    }

    public ApsUser playLists(Set<PlayList> playLists) {
        this.setPlayLists(playLists);
        return this;
    }

    public ApsUser addPlayList(PlayList playList) {
        this.playLists.add(playList);
        playList.setApsUser(this);
        return this;
    }

    public ApsUser removePlayList(PlayList playList) {
        this.playLists.remove(playList);
        playList.setApsUser(null);
        return this;
    }

    public Set<ArticleList> getArticleLists() {
        return this.articleLists;
    }

    public void setArticleLists(Set<ArticleList> articleLists) {
        if (this.articleLists != null) {
            this.articleLists.forEach(i -> i.setApsUser(null));
        }
        if (articleLists != null) {
            articleLists.forEach(i -> i.setApsUser(this));
        }
        this.articleLists = articleLists;
    }

    public ApsUser articleLists(Set<ArticleList> articleLists) {
        this.setArticleLists(articleLists);
        return this;
    }

    public ApsUser addArticleList(ArticleList articleList) {
        this.articleLists.add(articleList);
        articleList.setApsUser(this);
        return this;
    }

    public ApsUser removeArticleList(ArticleList articleList) {
        this.articleLists.remove(articleList);
        articleList.setApsUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApsUser)) {
            return false;
        }
        return getId() != null && getId().equals(((ApsUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApsUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", username='" + getUsername() + "'" +
            ", usernameVerified='" + getUsernameVerified() + "'" +
            ", privateEmail='" + getPrivateEmail() + "'" +
            ", privateVerified='" + getPrivateVerified() + "'" +
            ", language='" + getLanguage() + "'" +
            ", license='" + getLicense() + "'" +
            ", active='" + getActive() + "'" +
            ", password='" + getPassword() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
