import { Town } from './../enum/town.enum';
import { Campaign } from './../interface/campaign';
import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { tap, catchError } from "rxjs/operators";

import { CustomResponse } from "../interface/custom-response";

@Injectable({ providedIn: 'root' })
export class CampaignService {
    private readonly apiUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) { }

    campaigns$ = <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.apiUrl}/campaign/list`)
    .pipe(
        tap(console.log),
        catchError(this.handleError)
    );

    save$ = (campaign: Campaign) => <Observable<CustomResponse>>
    this.http.post<CustomResponse>(`${this.apiUrl}/campaign/save`, campaign)
    .pipe(
        tap(console.log),
        catchError(this.handleError)
    );

    filterCampaigns$ = (town: Town, response: CustomResponse) => <Observable<CustomResponse>>
    new Observable<CustomResponse>(
        suscriber => {
            console.log(response);
            suscriber.next(
                town === Town.ALL ? { ...response, message: `Campaigns filtered by ${town} city`} : 
                {
                    ...response,
                    message: response.data.campaigns
                    .filter(elem => {
                        if(elem.town === town) {
                            return elem
                        } else {
                            return undefined
                        }
                    }).length > 0 ? `Campaigns filtered by town ${town}` : `No campaigns of town ${town} found`,
                    data: { campaigns: response.data.campaigns
                    .filter(elem => {
                        if(elem.town === town) {
                            return elem
                        } else {
                            return undefined
                        }
                    })}
                }
            );
            suscriber.complete();
        }
    )
    .pipe(
        tap(console.log),
        catchError(this.handleError)
    );

    delete$ = (campaignId: number) => <Observable<CustomResponse>>
    this.http.delete<CustomResponse>(`${this.apiUrl}/campaign/delete/${campaignId}`)
    .pipe(
        tap(console.log),
        catchError(this.handleError)
    );

    edit$ = (campaign: Campaign) => <Observable<CustomResponse>>
    this.http.put<CustomResponse>(`${this.apiUrl}/campaign/edit/${campaign.id}`, campaign)
    .pipe(
        tap(console.log),
        catchError(this.handleError)
    );


    handleError(error: HttpErrorResponse): Observable<never> {
        console.log(error);
        return throwError (`An error ocured - Error code: ${error.status}, ${error.name}`);
    }
    
}