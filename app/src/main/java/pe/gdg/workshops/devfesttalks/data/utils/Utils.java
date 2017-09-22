/*
 * Copyright (c) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pe.gdg.workshops.devfesttalks.data.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * A collection of utility methods, all static.
 */
public class Utils {

    /*
     * Making sure public utility methods remain static
     */
    private Utils() {
    }

    /**
     * Returns if picture-in-picture (PIP) is supported by the system.
     *
     * @param context Used to query system features.
     * @return If the system supports PIP.
     */
    public static boolean supportsPictureInPicture(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_PICTURE_IN_PICTURE);
    }

    /**
     * Has permission boolean.
     *
     * @param context    the context
     * @param permission the permission
     * @return the boolean
     */
    public static boolean hasPermission(final Context context, final String permission) {
        return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(
                permission, context.getPackageName());
    }
}