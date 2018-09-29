using log4net;
using log4net.Config;
using Neomer.EveryPrice.SDK.Models;
using NHibernate;
using NHibernate.Cfg;
using NHibernate.Tool.hbm2ddl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Helpers
{
    public static class Logger
    {
        private static ILog log = LogManager.GetLogger("LOGGER");

        public static ILog Log
        {
            get { return log; }
        }

        public static void InitLogger(string filename)
        {
            XmlConfigurator.Configure(new System.IO.FileInfo(filename));
        }

        public static String PrepareEntity(IEntity entity)
        {
            return null;
        }
    }
}
