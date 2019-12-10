//tslint:disable

declare module "react-native-carrier-info" {
    export class CarrierInfo {
        static allowsVOIP(): Promise<boolean>;
        static carrierName(): Promise<string>;
        static isoCountryCode(): Promise<string>;
        static mobileCountryCode(): Promise<string>;
        static mobileNetworkCode(): Promise<string>;
        static mobileNetworkOperator(): Promise<string>;

        static addCarrierChangeListener(callback: (error: any, carrier: string[]) => void): any;
    }
}
