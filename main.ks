
connect disnake
where disnake.ext connect commands

bot = commands.Bot(intents=disnake.Intents.all(), command_prefix="None")

@bot.event
async block on_ready() ->
    print("i ready sucka!")
    variable = empty
    print(variable)
    if variable is "None" ->
        print("not print")
    <-
    so ->
        variable = false
    <-
    variable = <!> variable
    print(variable)
<-

bot.run("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")