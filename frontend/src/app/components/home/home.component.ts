import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PasteService } from '../../services/paste.service';
import { CreatePasteRequest, Paste } from '../../models/paste.model';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent {
    content: string = '';
    ttlSeconds: number | null = null;
    maxViews: number | null = null;

    isLoading: boolean = false;
    errorMessage: string = '';
    createdPaste: Paste | null = null;
    copied: boolean = false;

    constructor(
        private pasteService: PasteService,
        private router: Router
    ) { }

    createPaste(): void {
        if (!this.content.trim()) {
            this.errorMessage = 'Content cannot be empty';
            return;
        }

        this.isLoading = true;
        this.errorMessage = '';

        const request: CreatePasteRequest = {
            content: this.content
        };

        if (this.ttlSeconds && this.ttlSeconds > 0) {
            request.ttlSeconds = this.ttlSeconds;
        }

        if (this.maxViews && this.maxViews > 0) {
            request.maxViews = this.maxViews;
        }

        console.log('Creating paste with request:', request);
        this.pasteService.createPaste(request).subscribe({
            next: (paste) => {
                console.log('Paste created successfully:', paste);
                this.createdPaste = paste;
                this.isLoading = false;
            },
            error: (error) => {
                console.error('Error creating paste:', error);
                this.errorMessage = error.error?.message || error.message || 'Failed to create paste - Check if backend is running';
                this.isLoading = false;
            }
        });
    }

    copyUrl(): void {
        if (this.createdPaste) {
            const url = `${window.location.origin}/paste/${this.createdPaste.id}`;
            navigator.clipboard.writeText(url).then(() => {
                this.copied = true;
                setTimeout(() => this.copied = false, 2000);
            });
        }
    }

    viewPaste(): void {
        if (this.createdPaste) {
            this.router.navigate(['/paste', this.createdPaste.id]);
        }
    }

    reset(): void {
        this.content = '';
        this.ttlSeconds = null;
        this.maxViews = null;
        this.createdPaste = null;
        this.errorMessage = '';
    }
}
