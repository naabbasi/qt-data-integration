import React, {useRef} from 'react';

import './App.css';
import {Toast} from "primereact/toast";
import {Route, Routes} from "react-router-dom";

const Index = React.lazy(() => import('./components/index'));
const JadeCashInvoice = React.lazy(() => import('./components/integrations/jade-cash-invoice'));
const InvoicePayment = React.lazy(() => import('./components/integrations/invoice-payment'));


function App() {
    const toastRef = useRef(null as any);
    return (
        <div>
            <Toast ref={toastRef}></Toast>
            <Routes>
                {/*<Suspense fallback={<div>Loading...</div>}>
                    <Route path={"data-integration"} element={<Index/>}>
                        <Route path={"jade-cash-invoice"} element={<JadeCashInvoice/>}></Route>
                        <Route path={"payment"} element={<InvoicePayment/>}></Route>
                        <Route path={"404"} element={<div>Choose the correct path</div>}/>
                        <Route path="*" element={<Navigate replace to="404"/>}/>
                    </Route>
                </Suspense>*/}

                <Route path={"/data-integration"} element={<Index/>}>
                    <Route path={"/data-integration/jade-cash-invoice"} element={<JadeCashInvoice/>}></Route>
                    <Route path={"/data-integration/upload-payment"} element={<InvoicePayment/>}></Route>
                </Route>
            </Routes>
        </div>
    );
}

export default App;
