export interface MedicalCenterAddress {
    complete_address: string;
}

export interface MedicalCenter {
    id: any;
    name: string;
    addresses: MedicalCenterAddress[];
}