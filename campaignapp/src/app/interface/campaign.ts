import { Town } from './../enum/town.enum';
export interface Campaign {
    id: number;
    name: string;
    bidAmount: number;
    fund: number;
    status: boolean;
    town: Town;
    radiusKm: number;
}