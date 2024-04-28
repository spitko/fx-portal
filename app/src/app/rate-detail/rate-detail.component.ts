import {
  AfterContentInit,
  AfterViewInit,
  Component,
  ElementRef,
  Inject,
  Input,
  PLATFORM_ID,
  ViewChild,
} from '@angular/core';
import { RatesService } from '../services/rates.service';
import { ExchangeRate } from '../models/ExchangeRate';
import { Chart } from 'chart.js/auto';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-rate-detail',
  standalone: true,
  imports: [],
  templateUrl: './rate-detail.component.html',
  styleUrl: './rate-detail.component.css',
})
export class RateDetailComponent {
  chart!: Chart;

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
      });
    }
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
