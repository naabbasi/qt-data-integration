import {Menubar} from "primereact/menubar";
import {Outlet} from "react-router-dom";
import React from "react";

const items = [
    {
        label:'QTerminals Integrations Portal',
        icon:'pi pi-fw pi-home',
        /*items:[
            {
                label:'Home',
                icon:'pi pi-fw pi-home',
                url: '/data-integration'
            },
            {
                separator:true
            },
            {
                label:'Jade Cash Invoice',
                icon:'pi pi-fw pi-book',
                url: 'data-integration/jade-cash-invoice'
            },
            {
                label:'Payment',
                icon:'pi pi-fw pi-dollar',
                url: 'payment'
            }
        ]*/
    }
]

export const Index = () => {
    const start = <div>
        {/*<InputText placeholder="Search" type="text"/>*/}
    </div>;

    const end = <div>
        {/*<Button label="Logout" icon="pi pi-power-off"/>*/}
    </div>;

    return (
        <div>
            <Menubar model={items} start={start} end={end}/>
            <Outlet/>
        </div>
    );
}

export default Index;