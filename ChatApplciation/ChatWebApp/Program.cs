using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using ChatWebApp.Data;
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddDbContext<ChatWebAppContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("ChatWebAppContext") ?? throw new InvalidOperationException("Connection string 'ChatWebAppContext' not found.")));

// Add services to the container.
builder.Services.AddControllersWithViews();

var app = builder.Build();
/*builder.Services.AddCors(options =>
{
    options.AddPolicy("Allow All", builder =>
    {
        builder.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader();
    });
});*/

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
}
//todo
app.UseCors("Allow All");

app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Ranks}/{action=Index}/{id?}");

app.Run();
