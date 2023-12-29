package com.lpbank.balancefluctuation.model.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tran")
@Data
public class Trans {
        @XmlElement(name = "trnRefno")
        private String trnRefno;
        @XmlElement(name = "trnDT")
        private String trnDT;
        @XmlElement(name = "status")
        private String status;

}
