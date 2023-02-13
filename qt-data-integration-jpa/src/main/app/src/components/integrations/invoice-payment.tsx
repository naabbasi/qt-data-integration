import React, {useEffect, useRef, useState} from 'react';

import {Card} from "primereact/card";
import {HttpClient} from "../../config/http.client";
import {Button} from "primereact/button";
import {AppHelper, downloadFile} from "../../AppHelper";
import {InputTextarea} from "primereact/inputtextarea";
import {Toast} from "primereact/toast";
import {TabPanel, TabView} from "primereact/tabview";
import {Calendar} from "primereact/calendar";
import {ProgressBar} from "primereact/progressbar";

function InvoicePayment() {
    const toastRef = useRef(null as any);
    const effectRef = useRef(true);
    const [date, setDate] = useState<Date | Date[]>();
    const [lastRunDate, setLastRunDate] = useState<Date | Date[]>();
    const [serviceResponse, setServiceResponse] = useState("");
    const [endpointResponse, setEndpointResponse] = useState("");
    const [fileName, setFileName] = useState("");
    const [activeIndex, setActiveIndex] = useState(0);
    const [disable, setDisable] = useState(false);
    const [disableUploadPortalButton, setDisableUploadPortalButton] = useState(true);
    const httpClient: HttpClient = new HttpClient();
    let startInterval = false;
    let interval: string | number | NodeJS.Timer | undefined;

    //let jobNameProd = "QT_PAYMENT_TRANSFER%7C01.00.0000";
    let jobNameTest = "QT_PAYMEN_TRANSF_TO_BANK%7C01.00.0000";

    useEffect(() => {
        if(effectRef.current === true){
            //setDate(new Date());

            getLastRunDate().then( () => {
                checkStreamData().then( () => {
                    fetchEndpointInfo();
                });
            });

            effectRef.current = false;
        }

    }, []);

    const checkStreamData = async () => {
        let url = `${AppHelper.URL}/api/oci/events`;
        let eventSource = httpClient.stream(url);

        eventSource.addEventListener("data-integration", event => {
            let message = event.data + "\n";
            setEndpointResponse(endpointResponse => endpointResponse + message + "\n");
        });

        return Promise.resolve();
    }

    const fetchEndpointInfo = () => {
        let url = `${AppHelper.URL}/api/oci/integration/info`;
        httpClient.post(url, {"integrationId": jobNameTest}).then(res => {
            let message = res.data['paymentDate'] + "\n" + res.data['ociServiceStatus'] + "\n" + res.data['ociServiceBody'];
        }).catch(error => {
            setDisable(false);
            toastRef.current.show({severity: 'error', summary: 'Data Integration', detail: "Something went wrong"});
        });
    }

    const getLastRunDate = async () => {
        let url = `${AppHelper.URL}/api/payment/last-run-date`;
        httpClient.get(url).then(res => {
            if(res.status === 200){
                let lastRunDate = res.data['lastRunDate'];
                setLastRunDate(new Date(lastRunDate));
                return Promise.resolve(res.data);
            }
        }).catch(error => {
            toastRef.current.show({severity: 'error', summary: 'Data Integration', detail: "Unable to get Last Run Date"});
        });
    }

    const generatePayment = (event: any) => {
        let url = `${AppHelper.URL}/api/payment/generate`;

        setDisable(true);
        setFileName("");

        httpClient.post(url, {"integrationId": jobNameTest, "paymentDate": date}).then(res => {
            let message = res.data['paymentDate'] + "\n" + res.data['ociServiceStatus'] + "\n" + res.data['ociServiceBody'];
            setServiceResponse(message);
            if(res.data['ociServiceStatus'] === 204){
                displayGeneratedPaymentFile();
            } else {
                toastRef.current.show({severity: 'error', summary: 'Data Integration', detail: "Please check integration endpoint"});
            }

        }).catch(error => {
            setDisable(false);
            toastRef.current.show({severity: 'error', summary: 'Data Integration', detail: "Something went wrong"});
        });
    }

    const displayGeneratedPaymentFile = () => {
        let url = `${AppHelper.URL}/api/payment/payment-file-info`;
        httpClient.get(url).then(res => {
            if(res.data.length === 0 && startInterval !== true){
                interval = setInterval(()=> {
                    startInterval = true;
                    displayGeneratedPaymentFile();
                }, 10000);
            }

            if(res.data.length !== 0){
                setFileName("");

                for(let ftpFileName of res.data){
                    setFileName(ftpFileName);
                }

                setDisableUploadPortalButton(false);
                setDisable(false);
                setActiveIndex(1);
                toastRef.current.show({severity: 'success', summary: 'Data Integration', detail: "Download and Verify the Payment File"});
                clearInterval(interval);
            }
        }).catch(error => {
            setDisable(false);
            toastRef.current.show({severity: 'error', summary: 'Data Integration', detail: "Something went wrong"});
        });
    }

    const downloadGeneratedPaymentFile = () => {
        let url = `${AppHelper.URL}/api/payment/download/${fileName}`;
        httpClient.downloadFile(url).then(res => {
            downloadFile(res.data, fileName);
            toastRef.current.show({severity: 'success', summary: 'Data Integration', detail: "Download and Verify the Payment File"});
            setActiveIndex(1);
        }).catch(error => {
            setDisable(false);
            setDisableUploadPortalButton(true);
            toastRef.current.show({severity: 'error', summary: 'Data Integration', detail: "Something went wrong"});
        });
    }

    const uploadPaymentToBankPortal = (event: any) => {
        let url = `${AppHelper.URL}/api/payment/upload-to-bank`;
        httpClient.post(url, {"paymentDate": date}).then(res => {
            if(res.status === 200){
                setDisable(false);
                console.log(res.data);
                toastRef.current.show({severity: 'success', summary: 'Data Integration', detail: "File has been uploaded to Bank Portal"});
            }

        }).catch(error => {
            setDisable(false);
            toastRef.current.show({severity: 'error', summary: 'Data Integration', detail: "Something went wrong"});
        });
    }

    const footer = <div>
        <ProgressBar mode="indeterminate" className={disable ? "block" : "hidden"} />
        <TabView activeIndex={activeIndex} onTabChange={(e) => setActiveIndex(e.index)}>
            <TabPanel header="Endpoint Status">
                <InputTextarea style={{width: '-webkit-fill-available', height: '240px'}} readOnly={true}
                               value={endpointResponse}>
                </InputTextarea>
            </TabPanel>
            <TabPanel header="Generated Payment File">
                <Button label={fileName} className={fileName === "" ? "hidden" : "block"} onClick={downloadGeneratedPaymentFile}></Button>
            </TabPanel>
        </TabView>

    </div>

    return (
        <div>
            <Toast ref={toastRef}></Toast>
            <div className={"flex flex-row align-items-center justify-content-center"}>
                <Card className={"sm:w-6 md:w-6 lg:w-5 xl:4"} title={"Generate Payment"} footer={footer}>
                    <div className={"flex flex-row mb-2 align-items-baseline"}>
                        <label className={"sm:w-6 md:w-6 lg:w-6 xl:4"}>Run Date: </label>
                        <Calendar showIcon={true} value={date} dateFormat={"dd-mm-yy"} showTime={true} showSeconds={true}
                                  hourFormat={"24"} hideOnDateTimeSelect={true} onChange={(e) => {setDate(e.value)}}>
                        </Calendar>
                    </div>
                    <div className={"flex flex-row mb-2 align-items-baseline"}>
                        <label className={"sm:w-6 md:w-6 lg:w-6 xl:4"}>Last Run Date:</label>
                        <label>{`${lastRunDate}`}</label>
                    </div>
                    <div className={"flex flex-row align-items-baseline justify-content-center"}>
                        {/*<Button className={"mr-2 sm:w-6 md:w-6 lg:w-6 xl:4"} onClick={initialEvent}>Check Endpoint</Button>*/}
                        <Button className={"justify-content-center mr-2 sm:w-6 md:w-6 lg:w-6 xl:4"}
                                label={"Generate Report"}
                                disabled={disable} onClick={generatePayment}>
                        </Button>
                        <Button className={"justify-content-center mr-2 sm:w-6 md:w-6 lg:w-6 xl:4"}
                                label={"Upload QNB Portal"}
                                disabled={disableUploadPortalButton} onClick={uploadPaymentToBankPortal}>
                        </Button>
                    </div>
                </Card>
            </div>
        </div>
    );
}

export default InvoicePayment;
