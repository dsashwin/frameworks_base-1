/*
 * Copyright 2018 The Android Open Source Project
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

package android.media.update;

import android.app.PendingIntent;
import android.media.MediaItem2;
import android.media.MediaMetadata2;
import android.media.MediaPlayerInterface;
import android.media.MediaPlayerInterface.PlaybackListener;
import android.media.MediaSession2;
import android.media.MediaSession2.Command;
import android.media.MediaSession2.CommandButton;
import android.media.MediaSession2.CommandButton.Builder;
import android.media.MediaSession2.CommandGroup;
import android.media.MediaSession2.ControllerInfo;
import android.media.MediaSession2.PlaylistParams;
import android.media.MediaSession2.SessionCallback;
import android.media.SessionToken2;
import android.media.VolumeProvider2;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @hide
 */
// TODO: @SystemApi
public interface MediaSession2Provider extends TransportControlProvider {
    void close_impl();
    void setPlayer_impl(MediaPlayerInterface player);
    void setPlayer_impl(MediaPlayerInterface player, VolumeProvider2 volumeProvider);
    MediaPlayerInterface getPlayer_impl();
    SessionToken2 getToken_impl();
    List<ControllerInfo> getConnectedControllers_impl();
    void setCustomLayout_impl(ControllerInfo controller, List<CommandButton> layout);
    void setAudioFocusRequest_impl(int focusGain);

    void setAllowedCommands_impl(ControllerInfo controller, CommandGroup commands);
    void notifyMetadataChanged_impl();
    void sendCustomCommand_impl(ControllerInfo controller, Command command, Bundle args,
            ResultReceiver receiver);
    void sendCustomCommand_impl(Command command, Bundle args);
    void setPlaylist_impl(List<MediaItem2> playlist);
    List<MediaItem2> getPlaylist_impl();
    void setPlaylistParams_impl(PlaylistParams params);
    PlaylistParams getPlaylistParams_impl();

    void addPlaybackListener_impl(Executor executor, PlaybackListener listener);
    void removePlaybackListener_impl(PlaybackListener listener);

    interface CommandProvider {
        int getCommandCode_impl();
        String getCustomCommand_impl();
        Bundle getExtra_impl();
        Bundle toBundle_impl();

        boolean equals_impl(Object ob);
        int hashCode_impl();
    }

    interface CommandGroupProvider {
        void addCommand_impl(Command command);
        void addAllPredefinedCommands_impl();
        void removeCommand_impl(Command command);
        boolean hasCommand_impl(Command command);
        boolean hasCommand_impl(int code);
        Bundle toBundle_impl();
    }

    interface CommandButtonProvider {
        Command getCommand_impl();
        int getIconResId_impl();
        String getDisplayName_impl();
        Bundle getExtra_impl();
        boolean isEnabled_impl();

        interface BuilderProvider {
            Builder setCommand_impl(Command command);
            Builder setIconResId_impl(int resId);
            Builder setDisplayName_impl(String displayName);
            Builder setEnabled_impl(boolean enabled);
            Builder setExtra_impl(Bundle extra);
            CommandButton build_impl();
        }
    }

    interface ControllerInfoProvider {
        String getPackageName_impl();
        int getUid_impl();
        boolean isTrusted_impl();
        int hashCode_impl();
        boolean equals_impl(ControllerInfoProvider obj);
    }

    interface PlaylistParamsProvider {
        int getRepeatMode_impl();
        int getShuffleMode_impl();
        MediaMetadata2 getPlaylistMetadata_impl();
        Bundle toBundle_impl();
    }

    interface BuilderBaseProvider<T extends MediaSession2, C extends SessionCallback> {
        void setVolumeProvider_impl(VolumeProvider2 volumeProvider);
        void setSessionActivity_impl(PendingIntent pi);
        void setId_impl(String id);
        void setSessionCallback_impl(Executor executor, C callback);
        T build_impl();
    }
}
