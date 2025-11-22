-- Child monitoring events table optimized for Raspberry Pi

CREATE TABLE IF NOT EXISTS monitoring_events (
    id TEXT PRIMARY KEY,
    device_id TEXT NOT NULL,
    child_name TEXT,
    date TEXT NOT NULL,
    timestamp INTEGER NOT NULL,
    type TEXT NOT NULL,
    app TEXT,
    app_name TEXT,
    sender TEXT,
    receiver TEXT,
    content TEXT,
    conversation_id TEXT,
    created_at INTEGER DEFAULT (strftime('%s', 'now'))
);

-- CRITICAL INDEXES for fast queries
CREATE INDEX IF NOT EXISTS idx_device_date ON monitoring_events(device_id, date);
CREATE INDEX IF NOT EXISTS idx_device_timestamp ON monitoring_events(device_id, timestamp DESC);
CREATE INDEX IF NOT EXISTS idx_type ON monitoring_events(type);
CREATE INDEX IF NOT EXISTS idx_sender ON monitoring_events(sender);
CREATE INDEX IF NOT EXISTS idx_app ON monitoring_events(app);

-- Full-text search index for content (optional, enable if needed)
-- CREATE VIRTUAL TABLE monitoring_events_fts USING fts5(content, content=monitoring_events, content_rowid=rowid);

-- Update statistics for query optimizer
ANALYZE;
