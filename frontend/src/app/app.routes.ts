import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { PasteViewComponent } from './components/paste-view/paste-view.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'paste/:id', component: PasteViewComponent },
    { path: 'not-found', component: NotFoundComponent },
    { path: '**', redirectTo: 'not-found' }
];
