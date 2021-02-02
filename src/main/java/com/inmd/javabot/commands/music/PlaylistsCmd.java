/*
 * Copyright 2018 John Grosh <john.a.grosh@gmail.com>.
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
package com.inmd.javabot.commands.music;

import java.util.List;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.inmd.javabot.Bot;
import com.inmd.javabot.commands.MusicCommand;

/**
 *
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class PlaylistsCmd extends MusicCommand 
{
    public PlaylistsCmd(Bot bot)
    {
        super(bot);
        this.name = "playlists";
        this.help = "사용 가능한 재생 목록을 보여줌니다";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.guildOnly = true;
        this.beListening = false;
        this.beListening = false;
    }
    
    @Override
    public void doCommand(CommandEvent event) 
    {
        if(!bot.getPlaylistLoader().folderExists())
            bot.getPlaylistLoader().createFolder();
        if(!bot.getPlaylistLoader().folderExists())
        {
            event.reply(event.getClient().getWarning()+" 재생 목록 폴더가 존재하지 않아 만들 수 없습니다!");
            return;
        }
        List<String> list = bot.getPlaylistLoader().getPlaylistNames();
        if(list==null)
            event.reply(event.getClient().getError()+" 사용 가능한 재생 목록을로드하지 못했습니다!");
        else if(list.isEmpty())
            event.reply(event.getClient().getWarning()+" 재생 목록 폴더에 재생 목록이 없습니다!");
        else
        {
            StringBuilder builder = new StringBuilder(event.getClient().getSuccess()+"사용 가능한 재생 목록 :\n");
            list.forEach(str -> builder.append("`").append(str).append("` "));
            builder.append("\nType `").append(event.getClient().getTextualPrefix()).append("재생 목록을 재생하려면 `<prefix>play playlist <name>`을 (를) 재생하십시오");
            event.reply(builder.toString());
        }
    }
}
