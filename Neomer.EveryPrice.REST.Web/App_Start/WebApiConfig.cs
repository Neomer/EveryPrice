using Neomer.EveryPrice.SDK.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace Neomer.EveryPrice.REST.Web
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Конфигурация и службы веб-API

            // Маршруты веб-API
            config.MapHttpAttributeRoutes();

            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );

            NHibernateHelper.Instance.OpenSession(HttpContext.Current.Server.MapPath(@"~/Nhibernate.cfg.xml"));
            Logger.InitLogger(HttpContext.Current.Server.MapPath(@"~/Log4Net.cfg.xml"));

            var cfg = GlobalConfiguration.Configuration;
            cfg.Formatters.JsonFormatter.SerializerSettings.ReferenceLoopHandling = Newtonsoft.Json.ReferenceLoopHandling.Ignore;
            cfg.Formatters.JsonFormatter.SerializerSettings.DateFormatString = "yyyy-MM-dd'T'HH:mm:ss.fffK";
        }
    }
}
