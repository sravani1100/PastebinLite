import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { PasteService } from '../../services/paste.service';
import { Paste } from '../../models/paste.model';

@Component({
    selector: 'app-paste-view',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './paste-view.component.html',
    styleUrls: ['./paste-view.component.css']
})
export class PasteViewComponent implements OnInit {
    paste: Paste | null = null;
    isLoading: boolean = true;
    errorMessage: string = '';
    copied: boolean = false;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private pasteService: PasteService
    ) { }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.loadPaste(id);
        } else {
            this.router.navigate(['/not-found']);
        }
    }

    loadPaste(id: string): void {
        this.pasteService.getPaste(id).subscribe({
            next: (paste) => {
                this.paste = paste;
                this.isLoading = false;
            },
            error: (error) => {
                this.isLoading = false;
                if (error.status === 404) {
                    this.router.navigate(['/not-found']);
                } else {
                    this.errorMessage = 'Failed to load paste';
                }
            }
        });
    }

    copyContent(): void {
        if (this.paste) {
            navigator.clipboard.writeText(this.paste.content).then(() => {
                this.copied = true;
                setTimeout(() => this.copied = false, 2000);
            });
        }
    }

    copyUrl(): void {
        if (this.paste) {
            const url = window.location.href;
            navigator.clipboard.writeText(url).then(() => {
                this.copied = true;
                setTimeout(() => this.copied = false, 2000);
            });
        }
    }

    viewRaw(): void {
        if (this.paste) {
            window.open(`${window.location.origin}/api/pastes/${this.paste.id}/raw`, '_blank');
        }
    }

    goHome(): void {
        this.router.navigate(['/']);
    }

    formatDate(date: string): string {
        return new Date(date).toLocaleString();
    }
}
