package com.nichoshop.main.request;

import lombok.Data;
@Data
public class WatchListRequest {
    private int searchType; // 0 : all, 1: active 2: ended
    private int sort; // 0: ending soon 1: price lowest, 2: price highest 3: most recent
    private int curPage;
    private int pageStep;
}
