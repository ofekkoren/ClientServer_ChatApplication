using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using ChatWebApi.Data;
using ChatWebApi.Services;
using Microsoft.AspNetCore.Authentication.Cookies;
using ChatWebApi.Hubs;
using Microsoft.AspNetCore.SignalR;
using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddDbContext<ChatWebApiContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("ChatWebApiContext") ?? throw new InvalidOperationException("Connection string 'ChatWebApiContext' not found.")));

// Add services to the container.

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
/*builder.Services.AddSingleton<IUserService, UserService>();
builder.Services.AddSingleton<IContactService, ContactService>();
builder.Services.AddSingleton<IConversationService, ConversationService>();
builder.Services.AddSingleton<ChatWebApiContext>();
*/

builder.Services.AddControllersWithViews();

builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromDays(7);
});

builder.Services.AddAuthentication(options =>
{
    options.DefaultScheme = CookieAuthenticationDefaults.AuthenticationScheme;
}).AddCookie();

builder.Services.AddSignalR();

builder.Services.AddCors(options =>
{
    options.AddPolicy(name: "Allow all", policy =>
    {
        policy.SetIsOriginAllowed(param=>true).AllowAnyHeader().AllowAnyMethod().AllowCredentials();
    });
});

var app = builder.Build();
var defaultApp = FirebaseApp.Create(new AppOptions()
{
    Credential = GoogleCredential.FromFile(Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "firebasekey.json")),
});

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.UseCors("Allow all");
app.UseHttpsRedirection();
app.UseSession();
app.UseAuthentication();
app.UseAuthorization();
app.UseRouting();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Contacts}/{action=Index}/{id?}");

app.MapHub<AppHub>("/hubs/chatHub");

app.Run();