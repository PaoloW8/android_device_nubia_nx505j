/*
 * Copyright (c) 2017 The LineageOS Project
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * Also add information on how to contact you by electronic and paper mail.
 *
 */

package org.lineageos.flipflap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;


public class IconUtils {
    private static final String TAG = "IconUtils";
    private static boolean D = true;

    public static Bitmap getOverlaidBitmap(Resources res, int resId, int color) {
        return getOverlaidBitmap(res, resId, color, 0);
    }

    public static Bitmap getOverlaidBitmap(Resources res, int resId, int color, int density) {
        Bitmap src = getBitmapFromResource(res, resId, density);
        if (color == 0 || src == null) {
            return src;
        }

        final Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(dest);
        final Paint paint = new Paint();

        // Overlay the selected color and set the imageview
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        c.drawBitmap(src, 0, 0, paint);
        return dest;
    }

    public static Bitmap getBitmapFromResource(Resources res, int resId, int density) {
        if (density == 0) {
            if (D) Log.d(TAG, "Decoding resource id = " + resId + " for default density");
            return BitmapFactory.decodeResource(res, resId);
        }

        if (D) Log.d(TAG, "Decoding resource id = " + resId + " for density = " + density);
        Drawable d = res.getDrawableForDensity(resId, density);
        if (d instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) d;
            return bd.getBitmap();
        }

        Bitmap result = Bitmap.createBitmap(d.getIntrinsicWidth(),
                d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        d.setBounds(0, 0, result.getWidth(), result.getHeight());
        d.draw(canvas);
        canvas.setBitmap(null);

        return result;
    }

}
