package com.tms.v1.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Email.
 */
@Entity
@Table(name = "email")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "email")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userto")
    private String userto;

    @Column(name = "usercc")
    private String usercc;

    @Column(name = "userbcc")
    private String userbcc;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message")
    private String message;

    @Column(name = "multipart")
    private Boolean multipart;

    @Column(name = "html_body")
    private Boolean htmlBody;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    @Column(name = "attachment_name")
    private String attachmentName;

    @Column(name = "status")
    private String status;

    @Column(name = "sent_date_time")
    private Instant sentDateTime;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "email")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FileSystem> fileSystems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserto() {
        return userto;
    }

    public Email userto(String userto) {
        this.userto = userto;
        return this;
    }

    public void setUserto(String userto) {
        this.userto = userto;
    }

    public String getUsercc() {
        return usercc;
    }

    public Email usercc(String usercc) {
        this.usercc = usercc;
        return this;
    }

    public void setUsercc(String usercc) {
        this.usercc = usercc;
    }

    public String getUserbcc() {
        return userbcc;
    }

    public Email userbcc(String userbcc) {
        this.userbcc = userbcc;
        return this;
    }

    public void setUserbcc(String userbcc) {
        this.userbcc = userbcc;
    }

    public String getSubject() {
        return subject;
    }

    public Email subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public Email message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isMultipart() {
        return multipart;
    }

    public Email multipart(Boolean multipart) {
        this.multipart = multipart;
        return this;
    }

    public void setMultipart(Boolean multipart) {
        this.multipart = multipart;
    }

    public Boolean isHtmlBody() {
        return htmlBody;
    }

    public Email htmlBody(Boolean htmlBody) {
        this.htmlBody = htmlBody;
        return this;
    }

    public void setHtmlBody(Boolean htmlBody) {
        this.htmlBody = htmlBody;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public Email attachment(byte[] attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public Email attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public Email attachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
        return this;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getStatus() {
        return status;
    }

    public Email status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getSentDateTime() {
        return sentDateTime;
    }

    public Email sentDateTime(Instant sentDateTime) {
        this.sentDateTime = sentDateTime;
        return this;
    }

    public void setSentDateTime(Instant sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Email createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Email createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Email updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Email updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<FileSystem> getFileSystems() {
        return fileSystems;
    }

    public Email fileSystems(Set<FileSystem> fileSystems) {
        this.fileSystems = fileSystems;
        return this;
    }

    public Email addFileSystem(FileSystem fileSystem) {
        this.fileSystems.add(fileSystem);
        fileSystem.setEmail(this);
        return this;
    }

    public Email removeFileSystem(FileSystem fileSystem) {
        this.fileSystems.remove(fileSystem);
        fileSystem.setEmail(null);
        return this;
    }

    public void setFileSystems(Set<FileSystem> fileSystems) {
        this.fileSystems = fileSystems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Email)) {
            return false;
        }
        return id != null && id.equals(((Email) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Email{" +
            "id=" + getId() +
            ", userto='" + getUserto() + "'" +
            ", usercc='" + getUsercc() + "'" +
            ", userbcc='" + getUserbcc() + "'" +
            ", subject='" + getSubject() + "'" +
            ", message='" + getMessage() + "'" +
            ", multipart='" + isMultipart() + "'" +
            ", htmlBody='" + isHtmlBody() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            ", attachmentName='" + getAttachmentName() + "'" +
            ", status='" + getStatus() + "'" +
            ", sentDateTime='" + getSentDateTime() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
