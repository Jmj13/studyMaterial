package com.jagrutijadhav.diplomastudymaterial;

public class PDFData {
    String fileName;
    String fileURI;
    String fileDate;
    public PDFData(){}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileURI(String fileURI) {
        this.fileURI = fileURI;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public String getFileURI() {
        return fileURI;
    }

    public String getFileDate() {
        return fileDate;
    }

    public  PDFData(String fileName, String fileURL, String fileDate){
        this.fileDate=fileDate;
        this.fileName=fileName;
        this.fileURI=fileURL;
    }
}
