import React, {useEffect, useRef, useState} from 'react';

import {FileUpload} from "primereact/fileupload";
import {Card} from "primereact/card";
import {InputTextarea} from "primereact/inputtextarea";
import {ProgressBar} from "primereact/progressbar";
import {Toast} from "primereact/toast";
import {HttpClient} from "../../config/http.client";
import {AppHelper} from "../../AppHelper";

function JadeCashInvoice() {
    const fileUploadRef = useRef(null as any);
    const toastRef = useRef(null as any);
    const [fileStatus, setFileStatus] = useState("Click on choose button to upload JAD report");
    const [disable, setDisable] = useState(false);
    const httpClient: HttpClient = new HttpClient();

    useEffect(() => {

    }, []);

    const uploadFile = (event: any) => {
        setFileStatus("");
        if (typeof event.files !== 'undefined') {
            let url = `${AppHelper.URL}/api/jade-cash/upload`;
            const data = new FormData();
            for (let i = 0; i < event.files.length; i++) {
                if (event.files[i].name.indexOf('.xlsx') !== -1) {
                    data.append('jadeCashExcels', event.files[i]);
                } else {
                    setFileStatus("Unsupported file type");
                    fileUploadRef.current.clear();
                    toastRef.current.show({
                        severity: 'error',
                        summary: 'Data Integration',
                        detail: 'Unsupported file found'
                    });
                    return;
                }
            }

            setFileStatus("Please wait...");
            toastRef.current.show({severity: 'info', summary: 'Data Integration', detail: 'Process initiated'});
            setDisable(true);

            httpClient.post(url, data).then(res => {
                let message = res.data['fileStatus'] + "\n" + res.data['csvFile'] + "\n" + res.data['emptyTable'] + "\n";
                message += res.data['storeTable'] + "\n" + res.data['integrationFiles'] + "\n" + res.data['integrationFilesUploaded'];

                setFileStatus(message);
                if (fileUploadRef && fileUploadRef.current) {
                    fileUploadRef.current.clear();
                    setDisable(false);
                    toastRef.current.show({
                        severity: 'success',
                        summary: 'Data Integration',
                        detail: 'Process completed'
                    });
                }
            }).catch(error => {
                setFileStatus("Click on choose button to upload JAD report")
                setDisable(false);
                fileUploadRef.current.clear();
                toastRef.current.show({severity: 'error', summary: 'Data Integration', detail: "Something went wrong"});
            });
        }
    }

    const footer = <div>
        <InputTextarea style={{width: '-webkit-fill-available', height: '240px'}} readOnly={true} value={fileStatus}>
        </InputTextarea>
    </div>

    return (
        <div>
            <Toast ref={toastRef}></Toast>
            <div className={"flex flex-row flex-wrap align-items-center justify-content-center"}>
                <div className={"flex align-items-center justify-content-center w-5"}>
                    <Card title={"Upload Jade Cash Report"} subTitle={"Only JAD Report file(s) are supported"}
                          footer={footer} style={{width: '100%'}}>
                        <FileUpload ref={fileUploadRef} name="jadeCashExcels" disabled={disable} multiple={true}
                                    className={"align-items-center justify-content-center"}
                                    mode="advanced"
                                    accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                                    customUpload uploadHandler={uploadFile}
                                    onSelect={e => setFileStatus("Click on Upload button")}
                        >
                        </FileUpload>
                        <ProgressBar mode="indeterminate" className={disable ? "block" : "hidden"}/>
                    </Card>
                </div>
            </div>
        </div>
    );
}

export default JadeCashInvoice;
