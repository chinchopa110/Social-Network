package com.example.webapp1.Diaries.Posts.UserPostService;

import com.example.webapp1.Diaries.Posts.IPost;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

public class UserPostComparator implements Comparator<IPost> {
    @Override
    public int compare(IPost post1, IPost post2) {
        LocalDate date1 = LocalDate.parse(post1.getDate());
        LocalDate date2 = LocalDate.parse(post2.getDate());

        int dateComparison = date2.compareTo(date1);
        if (dateComparison != 0) {
            return dateComparison;
        }

        LocalTime time1 = LocalTime.parse(post1.getTime());
        LocalTime time2 = LocalTime.parse(post2.getTime());

        return time2.compareTo(time1);
    }
}
