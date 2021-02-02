/*
 * Copyright 2017 John Grosh <john.a.grosh@gmail.com>.
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
package com.inmd.javabot.commands.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.inmd.javabot.Bot;
import com.inmd.javabot.commands.OwnerCommand;
import net.dv8tion.jda.api.entities.Activity;


/**
 *
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class SetgameCmd extends OwnerCommand
{
    public SetgameCmd(Bot bot)
    {
        this.name = "setgame";
        this.help = "봇이 플레이하는 게임을 설정합니다.";
        this.arguments = "[action] [game]";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.guildOnly = false;
        this.children = new OwnerCommand[]{
            new SetlistenCmd(),
            new SetstreamCmd(),
            new SetwatchCmd()
        };
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        String title = event.getArgs().toLowerCase().startsWith("재생중") ? event.getArgs().substring(7).trim() : event.getArgs();
        try
        {
            event.getJDA().getPresence().setActivity(title.isEmpty() ? null : Activity.playing(title));
            event.reply(event.getClient().getSuccess()+" **"+event.getSelfUser().getName()
                    +"** "+(title.isEmpty() ? "더 이상 아무것도 연주하지 않습니다." : "지금 재생 중 `"+title+"`"));
        }
        catch(Exception e)
        {
            event.reply(event.getClient().getError()+" 게임을 설정할 수 없습니다!");
        }
    }
    
    private class SetstreamCmd extends OwnerCommand
    {
        private SetstreamCmd()
        {
            this.name = "stream";
            this.aliases = new String[]{"twitch","streaming"};
            this.help = "봇이 게임을 스트림으로 설정";
            this.arguments = "<username> <game>";
            this.guildOnly = false;
        }

        @Override
        protected void execute(CommandEvent event)
        {
            String[] parts = event.getArgs().split("\\s+", 2);
            if(parts.length<2)
            {
                event.replyError("트위치 사용자 이름과 게임 이름을 '스트리밍'에 포함하십시오.'");
                return;
            }
            try
            {
                event.getJDA().getPresence().setActivity(Activity.streaming(parts[1], "https://twitch.tv/"+parts[0]));
                event.replySuccess("**"+event.getSelfUser().getName()
                        +"** is now streaming `"+parts[1]+"`");
            }
            catch(Exception e)
            {
                event.reply(event.getClient().getError()+" 게임을 설정할 수 없습니다!");
            }
        }
    }
    
    private class SetlistenCmd extends OwnerCommand
    {
        private SetlistenCmd()
        {
            this.name = "listen";
            this.aliases = new String[]{"listening"};
            this.help = "봇이 듣고있는 게임을 설정합니다.";
            this.arguments = "<title>";
            this.guildOnly = false;
        }

        @Override
        protected void execute(CommandEvent event)
        {
            if(event.getArgs().isEmpty())
            {
                event.replyError("들을 제목을 포함시켜주세요!");
                return;
            }
            String title = event.getArgs().toLowerCase().startsWith("to") ? event.getArgs().substring(2).trim() : event.getArgs();
            try
            {
                event.getJDA().getPresence().setActivity(Activity.listening(title));
                event.replySuccess("**"+event.getSelfUser().getName()+"** 지금 듣고있습니다. `"+title+"`");
            } catch(Exception e) {
                event.reply(event.getClient().getError()+" 게임을 설정할 수 없습니다!");
            }
        }
    }
    
    private class SetwatchCmd extends OwnerCommand
    {
        private SetwatchCmd()
        {
            this.name = "watch";
            this.aliases = new String[]{"watching"};
            this.help = "봇이보고있는 게임을 설정";
            this.arguments = "<title>";
            this.guildOnly = false;
        }

        @Override
        protected void execute(CommandEvent event)
        {
            if(event.getArgs().isEmpty())
            {
                event.replyError("볼 제목을 포함하십시오!");
                return;
            }
            String title = event.getArgs();
            try
            {
                event.getJDA().getPresence().setActivity(Activity.watching(title));
                event.replySuccess("**"+event.getSelfUser().getName()+"** 지금보고있습니다. `"+title+"`");
            } catch(Exception e) {
                event.reply(event.getClient().getError()+" 게임을 설정할 수 없습니다!");
            }
        }
    }
}
