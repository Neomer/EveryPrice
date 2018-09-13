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
    public class NHibernateHelper
    {
        private static NHibernateHelper instance;
        private ISessionFactory sessionFactory = null;

        private NHibernateHelper()
        {
        }

        public static NHibernateHelper Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new NHibernateHelper();
                }
                return instance;
            }
        }

        public void OpenSession(string configurationFilePath)
        {
            var configuration = new Configuration();
            configuration.Configure(configurationFilePath);
            configuration.AddAssembly(typeof(IEntity).Assembly);
            sessionFactory = configuration.BuildSessionFactory();

            new SchemaUpdate(configuration).Execute(true, true);

            CurrentSession = sessionFactory.OpenSession();
        }

        public void CloseSession()
        {
            if (sessionFactory != null)
            {
                sessionFactory.Close();
            }
        }

        public ISession CurrentSession { get; private set; }

    }
}
