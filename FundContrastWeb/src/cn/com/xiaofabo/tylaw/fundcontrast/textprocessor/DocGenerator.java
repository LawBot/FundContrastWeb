/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.tylaw.fundcontrast.textprocessor;

import java.io.IOException;
import java.util.List;

import cn.com.xiaofabo.tylaw.fundcontrast.entity.FundDoc;
import cn.com.xiaofabo.tylaw.fundcontrast.entity.PatchDto;
import cn.com.xiaofabo.tylaw.fundcontrast.utils.CompareUtils;
import cn.com.xiaofabo.tylaw.fundcontrast.utils.DataUtils;
import cn.com.xiaofabo.tylaw.fundcontrast.utils.GenerateCompareDoc;

/**
 *
 * @author 陈光曦
 */
public class DocGenerator {

    public static String LEADING_TEXT_STOCK = "（以下简称“《基金合同》”）系按照中国证监"
            + "会基金监管部发布的《证券投资基金基金合同填报指引第1号——股票型（混合型）"
            + "证券投资基金基金合同填报指引（试行）》（以下简称“《指引》”）撰写。"
            + "根据基金托管人和律师事务所的意见，我公司在撰写《基金合同》时对"
            + "《指引》部分条款进行了增加、删除或修改，现将具体情况详细说明如下。";
    public static String LEADING_TEXT_INDEX = "（以下简称“《基金合同》”）系按照中国证监"
            + "会基金监管部发布的《证券投资基金基金合同填报指引第2号——"
            + "指数型证券投资基金基金合同填报指引（试行）》（以下简称“《指引》”）撰写。"
            + "根据基金托管人和律师事务所的意见，我公司在撰写《基金合同》时对"
            + "《指引》部分条款进行了增加、删除或修改，现将具体情况详细说明如下。";
    public static String LEADING_TEXT_BOND = "（以下简称“《基金合同》”）系按照中国证监"
            + "会基金监管部发布的《证券投资基金基金合同填报指引第3号——"
            + "证券投资基金基金合同填报指引（试行）》（以下简称“《指引》”）撰写。"
            + "根据基金托管人和律师事务所的意见，我公司在撰写《基金合同》时对《指引》部分"
            + "条款进行了增加、删除或修改，现将具体情况详细说明如下。";
    public static String LEADING_TEXT_CURRENCY = "（以下简称“《基金合同》”）系按照中国"
            + "证监会基金监管部发布的《证券投资基金基金合同填报指引第4号——货币市场基金基金合同"
            + "填报指引（试行）》（以下简称“《指引》”）撰写。根据基金托管人和律师事务所的意见，"
            + "我公司在撰写《基金合同》时对《指引》部分条款进行了增加、删除或修改，"
            + "现将具体情况详细说明如下。";

//    public static int STATUS_SUCCESS = 0;
    
    public static int STATUS_SUCCESS_GYRX_STOCK = 10;
    public static int STATUS_SUCCESS_GYRX_INDEX = 11;
    public static int STATUS_SUCCESS_GYRX_BOND = 12;
    public static int STATUS_SUCCESS_GYRX_CURRENCY = 13;
    
    public static int STATUS_SUCCESS_HXJJ_STOCK = 20;
    public static int STATUS_SUCCESS_HXJJ_INDEX = 21;
    public static int STATUS_SUCCESS_HXJJ_BOND = 22;
    public static int STATUS_SUCCESS_HXJJ_CURRENCY = 23;
    
    public static int STATUS_SUCCESS_JTJJ_STOCK = 30;
    public static int STATUS_SUCCESS_JTJJ_INDEX = 31;
    public static int STATUS_SUCCESS_JTJJ_BOND = 32;
    public static int STATUS_SUCCESS_JTJJ_CURRENCY = 33;
    
    public static int STATUS_ERROR_UNKNOWN = -1;
    public static int STATUS_ERROR_FUND_TYPE_UNKNOWN = -2;
    public static int STATUS_ERROR_FUND_EST_UNKNOWN = -3;
    public static int STATUS_ERROR_INPUT_CANNOT_READ = -10;
    public static int STATUS_ERROR_INPUT_IO_ERROR = -11;
    public static int STATUS_ERROR_OUTPUT_CANNOT_GENERATE = -20;
    public static int STATUS_ERROR_OUTPUT_IO_ERROR = -21;
    public static int STATUS_ERROR_TEMPLATE_NOT_FOUND = -30;
    public static int STATUS_ERROR_TEMPLATE_IO_ERROR = -31;
    /**
     * @return 
     * @param inputSampleDocPath
     * @param outputDocPath
     * @return
     */
    public static int generate(String inputSampleDocPath, String outputDocPath,String templatePathDir) {
    	int statusCode = 0;
        DocProcessor sampleDocProcessor = new DocProcessor(inputSampleDocPath);
        try {
            sampleDocProcessor.readText(inputSampleDocPath);
        } catch (IOException e) {
            /// Read input sample document IO error
            return STATUS_ERROR_INPUT_IO_ERROR;
        }

        FundDoc sampleDoc = sampleDocProcessor.process();

        if (sampleDoc.getType() == FundDoc.CONTRACT_TYPE_UNKNOWN) {
            return STATUS_ERROR_FUND_TYPE_UNKNOWN;
        }
        if(sampleDoc.getEstablisher().equals(FundDoc.CONTRACT_ESTABLISHER_GYRX)) {
        	statusCode += 10;
        }else if(sampleDoc.getEstablisher().equals(FundDoc.CONTRACT_ESTABLISHER_HXJJ)) {
        	statusCode += 20;
        }else if(sampleDoc.getEstablisher().equals(FundDoc.CONTRACT_ESTABLISHER_JTJJ)) {
        	statusCode += 30;
        }else {
            return STATUS_ERROR_FUND_EST_UNKNOWN;
        }

        String templateDocPath;
        switch (sampleDoc.getType()) {
            case 0:
            	statusCode += 0;
                templateDocPath = templatePathDir+"/"+DataUtils.STANDARD_TYPE_STOCK_C;
                break;
            case 1:
            	statusCode += 1;
                templateDocPath = templatePathDir+"/"+DataUtils.STANDARD_TYPE_INDEX;
                break;
            case 2:
            	statusCode += 2;
                templateDocPath = templatePathDir+"/"+DataUtils.STANDARD_TYPE_BOND;
                break;
            case 3:
            	statusCode += 3;
                templateDocPath = templatePathDir+"/"+DataUtils.STANDARD_TYPE_MONETARY;
                break;
            default:
                templateDocPath = "";
        }

        if (templateDocPath.isEmpty()) {
            return STATUS_ERROR_TEMPLATE_NOT_FOUND;
        }

        DocProcessor templateDocProcessor = new DocProcessor(templateDocPath);
        try {
            templateDocProcessor.readText(templateDocPath);
        } catch (IOException e) {
            /// Read input sample document IO error
            return STATUS_ERROR_TEMPLATE_IO_ERROR;
        }
        FundDoc templateDoc = templateDocProcessor.process();

        String outputFileTitle = "《" + sampleDoc.getContractNameComplete() + "》";
        StringBuilder leadingTextSB = new StringBuilder();

        leadingTextSB.append(sampleDoc.getContractName());
        leadingTextSB.append("募集申请材料之");
        leadingTextSB.append(outputFileTitle);
        switch (sampleDoc.getType()) {
            case 0:
                leadingTextSB.append(LEADING_TEXT_STOCK);
                break;
            case 1:
                leadingTextSB.append(LEADING_TEXT_INDEX);
                break;
            case 2:
                leadingTextSB.append(LEADING_TEXT_BOND);
                break;
            case 3:
                leadingTextSB.append(LEADING_TEXT_CURRENCY);
                break;
            default:
                break;
        }
        String leadingText = leadingTextSB.toString();

        GenerateCompareDoc genDoc = new GenerateCompareDoc();
        List<PatchDto> patchDtoList;
        CompareUtils compareUtils = new CompareUtils();
        try {
            patchDtoList = compareUtils.getPatchDtoList(templateDocPath, inputSampleDocPath);
        } catch (Exception e) {
            return STATUS_ERROR_UNKNOWN;
        }

        try {
            genDoc.generate(outputFileTitle, leadingText, patchDtoList, outputDocPath);
        } catch (IOException e) {
            e.printStackTrace();
            return STATUS_ERROR_OUTPUT_IO_ERROR;
        }

        return statusCode;
    }
}
