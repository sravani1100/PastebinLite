export interface Paste {
  id: string;
  content: string;
  url: string;
  createdAt: string;
  expiresAt: string | null;
  maxViews: number | null;
  currentViews: number;
}

export interface CreatePasteRequest {
  content: string;
  ttlSeconds?: number;
  maxViews?: number;
}
