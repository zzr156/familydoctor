package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 *
 * Created by WangCheng on 2018/11/15.
 */
@Entity
@Table(name = "CD_PAPER_MANAGE")
public class CdPaperManage extends BasePO{

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;

    @Column(name = "DOCUMENT_TITLE", length = 40)
    private String documentTitle;//文书标题

    @Column(name = "DOCUMENT_PATH")
    private String documentPath;//文书路径

    @Column(name = "DOCUMENT_CODE", length = 100)
    private String documentCode;//文书代码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }
}
