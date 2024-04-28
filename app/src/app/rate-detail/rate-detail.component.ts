import { isPlatformBrowser } from '@angular/common';
import { Component, Inject, Input, PLATFORM_ID } from '@angular/core';
import { Chart } from 'chart.js/auto';
import { ExchangeRate } from '../models/ExchangeRate';
import { RatesService } from '../services/rates.service';

@Component({
  selector: 'app-rate-detail',
  standalone: true,
  imports: [],
  templateUrl: './rate-detail.component.html',
  styleUrl: './rate-detail.component.css',
})
export class RateDetailComponent {
  public amount: number = 1;
  public convertedAmount: string = '0';

  chart!: Chart;
  public currentRate?: ExchangeRate;

  constructor(
    private ratesService: RatesService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.initChart();
    }
  }

  @Input('currency')
  set id(currency: string) {
    if (isPlatformBrowser(this.platformId)) {
      this.ratesService.getHistorical(currency).subscribe((data) => {
        this.updateChart(data);
        this.currentRate = data[data.length - 1];
        this.convertedAmount = (
          this.amount * Number(this.currentRate?.rate)
        ).toFixed(2);
      });
    }
  }

  onAmountChange(event: any) {
    const newValue = event.target.value;
    if (!newValue) {
      this.amount = 0;
      this.convertedAmount = '0';
      return;
    }
    this.amount = Number(newValue);
    this.convertedAmount = (
      this.amount * Number(this.currentRate?.rate)
    ).toFixed(2);
  }

  private initChart() {
    this.chart = new Chart('RateChart', {
      type: 'line',
      data: {
        labels: [],
        datasets: [
          {
            label: '',
            data: [],
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1,
          },
        ],
      },
    });
  }

  private updateChart(data: ExchangeRate[]) {
    this.chart.data.labels = data.map((rate) => rate.date);
    this.chart.data.datasets[0].data = data.map((rate) => Number(rate.rate));
    this.chart.data.datasets[0].label = data[0].currency;
    this.chart.update();
  }
}
