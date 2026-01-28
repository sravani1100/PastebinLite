import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Paste, CreatePasteRequest } from '../models/paste.model';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class PasteService {
    private apiUrl = environment.apiUrl;

    constructor(private http: HttpClient) { }

    createPaste(request: CreatePasteRequest): Observable<Paste> {
        return this.http.post<Paste>(`${this.apiUrl}/pastes`, request);
    }

    getPaste(id: string): Observable<Paste> {
        return this.http.get<Paste>(`${this.apiUrl}/pastes/${id}`);
    }

    getRawPaste(id: string): Observable<string> {
        return this.http.get(`${this.apiUrl}/pastes/${id}/raw`, { responseType: 'text' });
    }

    deletePaste(id: string): Observable<any> {
        return this.http.delete(`${this.apiUrl}/pastes/${id}`);
    }
}
