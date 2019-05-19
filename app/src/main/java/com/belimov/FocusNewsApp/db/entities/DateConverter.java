package com.belimov.FocusNewsApp.db.entities;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Date convertToDate(Long dateInMills) {
        return dateInMills != null ? new Date(dateInMills) : null;
    }

    @TypeConverter
    public Long convertToMills(Date date) {
        return date != null ? date.getTime() : null;
    }
}
