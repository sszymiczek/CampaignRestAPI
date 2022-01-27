import { Campaign } from './interface/campaign';
import { Town } from './enum/town.enum';
import { DataState } from './enum/data-state.enum';
import { CustomResponse } from './interface/custom-response';
import { AppState } from './interface/app-state';
import { Observable, of, startWith, map, catchError, BehaviorSubject, tap } from 'rxjs';
import { CampaignService } from './service/campaign.service';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{
  public appState$: Observable<AppState<CustomResponse>>;
  readonly DataState = DataState;
  readonly Town = Town;
  private dataSubject = new BehaviorSubject<CustomResponse>(null);
  public empSelected: Town;
  ngForm: NgForm;

  constructor(private campaignService: CampaignService) {}

  ngOnInit(): void {
    this.appState$ = this.campaignService.campaigns$
    .pipe(
      map(response => {
        this.dataSubject.next(response);
        return { dataState: DataState.LOADED_STATE, appData: response }
      }),
      startWith({ dataState: DataState.LOADING_STATE }),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE, error })
      })
    );
    this.empSelected = Town.ALL
  }

  filterCampaigns(): void {
    this.appState$ = this.campaignService.filterCampaigns$(this.empSelected, this.dataSubject.value)
    .pipe(
      map(response => {
        return { dataState: DataState.LOADED_STATE, appData: response }
      }),
      startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE, error })
      })
    )
  }

  saveCampaign(campaignForm: NgForm): void {
    this.appState$ = this.campaignService.save$(<Campaign> campaignForm.value)
    .pipe(
      map(response => {
        this.dataSubject.next(
          {...response, data: {campaigns: [response.data.campaign, ...this.dataSubject.value.data.campaigns]}}
        );
        campaignForm.resetForm()
        return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}
      }),
      startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE, error })
      })
    )
  }

  deleteCampaign(campaign: Campaign): void {
    this.appState$ = this.campaignService.delete$(campaign.id)
    .pipe(
      map(response => {
        this.dataSubject.next(
          {...response, data: 
            { campaigns: this.dataSubject.value.data.campaigns.filter(c => c.id !== campaign.id)}}
        );
        return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}
      }),
      startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE, error })
      })
    )
  }

  editCampaign(campaignEditForm: NgForm): void {
    this.appState$ = this.campaignService.edit$(<Campaign> campaignEditForm.value)
    .pipe(
      map(response => {
        this.dataSubject.next(response);
        return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}
      }),
      startWith({ dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE, error })
      })
    )
  }
}
