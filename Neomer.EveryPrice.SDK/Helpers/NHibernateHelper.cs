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

        private NHibernateTransactionsProvider transactionsProvider;

        private NHibernateHelper()
        {
            transactionsProvider = new NHibernateTransactionsProvider();
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

        public Guid BeginTransaction()
        {
            var transactionUid = Guid.NewGuid();
            transactionsProvider.RegisterTransaction(transactionUid, CurrentSession.BeginTransaction());
            return transactionUid;
        }

        public void CommitTransaction(Guid key)
        {
            try
            {
                transactionsProvider.CommitTransaction(key);
            }
            catch (Exception ex)
            {
                transactionsProvider.RollbackTransaction(key);
                throw ex;
            }
        }

        public void RollbackTransaction(Guid key)
        {
            transactionsProvider.RollbackTransaction(key);
        }
    }

    public class NHibernateTransactionsProvider {

        private IDictionary<Guid, ITransaction> transactionList;

        public NHibernateTransactionsProvider()
        {
            transactionList = new Dictionary<Guid, ITransaction>();
        }

        public void RegisterTransaction(Guid key, ITransaction transaction)
        {
            transactionList[key] = transaction;
        }

        public void CommitTransaction(Guid key)
        {
            if (transactionList.ContainsKey(key))
            {
                transactionList[key].Commit();
            }
        }

        public void RollbackTransaction(Guid key)
        {
            if (transactionList.ContainsKey(key))
            {
                transactionList[key].Rollback();
            }
        }
    }

}
