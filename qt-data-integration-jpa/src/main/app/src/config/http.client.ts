import axios, {AxiosInstance} from "axios";

export class HttpClient {
    private static axiosClient: AxiosInstance;

    constructor() {
        HttpClient.axiosClient = axios.create({
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer Implemented Later...'
            },
            withCredentials: true
        });
    }

    private getHttpClient() : AxiosInstance{
        return HttpClient.axiosClient;
    }

    public get(url: string, data?: any) {
        return this.getHttpClient().get(url, data);
    }

    public post(url: string, data?: any) {
        return this.getHttpClient().post(url, data);
    }

    public stream(url: string) : EventSource {
        const eventSource = new EventSource(url, { withCredentials: true } );
        return eventSource;
    }

    public downloadFile(url: string) {
        return this.getHttpClient().get(url, { withCredentials: true, responseType: "arraybuffer"});
    }
}