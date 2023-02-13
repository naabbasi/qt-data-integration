export const AppHelper = {
    URL: `http://qtvmmdqbill01.qterminals.local:8484/data-integration`
    //URL: `http://localhost:8080/data-integration`
};

export function downloadFile(data: any, fileName: string) {
    const blob = new Blob([data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    });

    let url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `${fileName}`);
    document.body.appendChild(link);
    link.click();
}