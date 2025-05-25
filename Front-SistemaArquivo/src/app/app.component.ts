import { Component } from '@angular/core';
import {MatTabsModule} from '@angular/material/tabs';
import { NoPatternComponent } from './noPattern/no-pattern/no-pattern.component'
import { WithPatternComponent } from './withPattern/with-pattern/with-pattern.component';

@Component({
  selector: 'app-root',
  imports: [
    MatTabsModule,
    NoPatternComponent,
    WithPatternComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Sistema de Arquivos';
}
