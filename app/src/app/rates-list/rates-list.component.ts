import { Component } from '@angular/core';
import { RatesService } from '../services/rates.service';
import { ExchangeRate } from '../models/ExchangeRate';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-rates-list',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './rates-list.component.html',
  styleUrl: './rates-list.component.css',
})
export class RatesListComponent {
  rates?: ExchangeRate[];

  constructor(private ratesService: RatesService) {}

  ngOnInit() {
    this.ratesService.getCurrent().subscribe((data) => {
      this.rates = data;
    });
  }
}
